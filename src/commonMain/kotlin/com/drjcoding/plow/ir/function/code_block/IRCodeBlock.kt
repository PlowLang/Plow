package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.UnitIRType
import com.drjcoding.plow.llvm.code_block.LLVMFunctionBody
import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.LLVMSection
import com.drjcoding.plow.llvm.code_block.statement.*

class IRCodeBlock(
    private val statements: List<IRStatement>
) {
    private var variables: MutableSet<IRLocalVariable> = mutableSetOf()

    constructor() : this(listOf())

    constructor(vararg statements: IRStatement) : this(statements.toList())

    operator fun plus(other: IRCodeBlock) = IRCodeBlock(this.statements + other.statements).also {
        it.variables = (this.variables + other.variables).toMutableSet()
    }

    operator fun plus(statement: IRStatement) = IRCodeBlock(this.statements + statement).also {
        it.variables = this.variables
    }

    fun createNewLocalVariable(type: IRType): IRLocalVariable {
        val variable = IRLocalVariable(type)
        variables.add(variable)
        return variable
    }

    private fun getLabels(): List<IRStatement.Label> = statements.filterIsInstance<IRStatement.Label>()

    fun toLLVMFunctionBody(arguments: List<IRLocalVariable>, returnType: IRType): LLVMFunctionBody {
        var idNum = 0
        fun newID(): LLVMLocalID = LLVMLocalID(idNum++)

        val localVarsToID: MutableMap<IRLocalVariable, LLVMLocalID> = mutableMapOf()
        arguments.forEach {
            // arguments are automatically associated tih %0, %1, etc. They do not need to be initialized
            localVarsToID[it] = newID()
        }

        // associate ids with labels
        val labelIDs = getLabels().associateWith { newID() }

        val sections = mutableListOf<LLVMSection>()
        var currentSectionID = newID() // the initial section has this default label
        var currentSectionStatements = mutableListOf<LLVMStatement>()
        fun newSection(id: LLVMLocalID) {
            sections.add(LLVMSection(currentSectionID, currentSectionStatements))
            currentSectionID = id
            currentSectionStatements = mutableListOf()
        }

        fun newSection(label: IRStatement.Label) = newSection(labelIDs[label]!!)

        // start by allocating all the local variables
        variables.forEach {
            if (it in arguments) return@forEach // we don't need to allocate space for arguments

            val id = newID()
            localVarsToID[it] = id
            currentSectionStatements += LLVMAllocA(id, it.type.toLLVM())
        }

        // now we can iterate through the statements
        for (statement in statements) {
            when (statement) {
                is IRStatement.Assignment -> {
                    val assignTo = localVarsToID[(statement.to as IRAssignable.LocalVariable).variable]!!
                    currentSectionStatements += LLVMAssignment(
                        assignTo,
                        statement.value.toLLVM(localVarsToID)!! // TODO check for void. This can crash
                    )
                }
                is IRStatement.Jump -> {
                    if (statement.condition != null) {
                        val specifiedLabel = labelIDs[statement.label]!!
                        val otherLabel = newID()
                        val condition =
                            statement.condition.toLLVM(localVarsToID)!! // we already checked that this is a boolean
                        val ifTrue = if (statement.inverted) otherLabel else specifiedLabel
                        val ifFalse = if (statement.inverted) specifiedLabel else otherLabel
                        currentSectionStatements += LLVMConditionalJump(condition, ifTrue, ifFalse)
                        newSection(otherLabel)
                    } else {
                        currentSectionStatements += LLVMUnconditionalJump(labelIDs[statement.label]!!)
                    }
                }
                is IRStatement.Label -> {
                    currentSectionStatements += LLVMUnconditionalJump(labelIDs[statement]!!)
                    newSection(statement)
                }
                is IRStatement.Return -> currentSectionStatements += LLVMRet(statement.value.toLLVM(localVarsToID))
                is IRStatement.FunctionCall -> {
                    val assignTo = localVarsToID[(statement.to as IRAssignable.LocalVariable).variable]!!
                    currentSectionStatements += LLVMFunctionCall(
                        assignTo,
                        statement.function.toLLVM(localVarsToID)!!,
                        statement.arguments.map { it.toLLVM(localVarsToID)!! }
                    )
                }
            }
        }

        if (returnType == UnitIRType) currentSectionStatements += LLVMRet(null)
        sections.add(LLVMSection(currentSectionID, currentSectionStatements))

        return LLVMFunctionBody(sections)
    }
}