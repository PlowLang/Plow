package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.globals.IRGlobal
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.StandardTypes
import com.drjcoding.plow.ir.type.UnitIRType
import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.statement.LLVMLoad
import com.drjcoding.plow.llvm.code_block.statement.LLVMStatement
import com.drjcoding.plow.llvm.code_block.value.LLVMGlobalValue
import com.drjcoding.plow.llvm.code_block.value.LLVMIntLiteral
import com.drjcoding.plow.llvm.code_block.value.LLVMStackLocalValue
import com.drjcoding.plow.llvm.code_block.value.LLVMValue
import com.drjcoding.plow.llvm.mangle


data class IntLiteralIRValue(val int: Int) : IRValue {
    override val type = StandardTypes.INT_IR_TYPE

    override fun toLLVM(
        locals: Map<IRLocalVariable, LLVMLocalID>,
        idAllocator: () -> LLVMLocalID
    ): Pair<List<LLVMStatement>, LLVMValue?> = listOf<LLVMStatement>() to LLVMIntLiteral(int, type.toLLVM())
}

object UnitIRValue : IRValue {
    override val type = UnitIRType

    override fun toLLVM(
        locals: Map<IRLocalVariable, LLVMLocalID>,
        idAllocator: () -> LLVMLocalID
    ): Pair<List<LLVMStatement>, LLVMValue?> = listOf<LLVMStatement>() to null
}

data class LocalVariableIRValue(val localVariable: IRLocalVariable) : IRValue {
    override val type = localVariable.type

    override fun toLLVM(
        locals: Map<IRLocalVariable, LLVMLocalID>,
        idAllocator: () -> LLVMLocalID
    ): Pair<List<LLVMStatement>, LLVMValue?> {
        return if (localVariable.isArg) {
            listOf<LLVMStatement>() to LLVMStackLocalValue(locals[localVariable]!!, localVariable.type.toLLVM())
        } else {
            val newLocal = idAllocator()

            val pre = listOf(
                LLVMLoad(newLocal, locals[localVariable]!!, localVariable.type.toLLVM())
            )

            pre to LLVMStackLocalValue(newLocal, type.toLLVM())

        }
    }
}

data class GlobalIRValue(val global: IRGlobal) : IRValue {
    override val type = global.type

    override fun toLLVM(
        locals: Map<IRLocalVariable, LLVMLocalID>,
        idAllocator: () -> LLVMLocalID
    ): Pair<List<LLVMStatement>, LLVMValue?> =
        listOf<LLVMStatement>() to LLVMGlobalValue(if (global.noMangle) global.name else mangle(global.scope, global.name), type.toLLVM())
}

interface IRValue {
    val type: IRType
    fun toLLVM(
        locals: Map<IRLocalVariable, LLVMLocalID>,
        idAllocator: () -> LLVMLocalID
    ): Pair<List<LLVMStatement>, LLVMValue?>
}