package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.globals.IRGlobal
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.StandardTypes
import com.drjcoding.plow.ir.type.UnitIRType
import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMGlobalValue
import com.drjcoding.plow.llvm.code_block.value.LLVMIntLiteral
import com.drjcoding.plow.llvm.code_block.value.LLVMStackLocalValue
import com.drjcoding.plow.llvm.code_block.value.LLVMValue
import com.drjcoding.plow.llvm.mangle


class IntLiteralIRValue(val int: Int) : IRValue {
    override val type = StandardTypes.INT_IR_TYPE

    override fun toLLVM(locals: Map<IRLocalVariable, LLVMLocalID>) = LLVMIntLiteral(int, type.toLLVM())
}

object UnitIRValue : IRValue {
    override val type = UnitIRType

    override fun toLLVM(locals: Map<IRLocalVariable, LLVMLocalID>) = null
}

class LocalVariableIRValue(val localVariable: IRLocalVariable) : IRValue {
    override val type = localVariable.type

    override fun toLLVM(locals: Map<IRLocalVariable, LLVMLocalID>) =
        LLVMStackLocalValue(locals[localVariable]!!, type.toLLVM())
}

class GlobalIRValue(val global: IRGlobal) : IRValue {
    override val type = global.type

    override fun toLLVM(locals: Map<IRLocalVariable, LLVMLocalID>) =
        LLVMGlobalValue(mangle(global.scope, global.name), type.toLLVM())
}

interface IRValue {
    val type: IRType
    fun toLLVM(locals: Map<IRLocalVariable, LLVMLocalID>): LLVMValue?
}