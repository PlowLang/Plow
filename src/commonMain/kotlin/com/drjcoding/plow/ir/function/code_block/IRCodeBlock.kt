package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.type.IRType

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
}