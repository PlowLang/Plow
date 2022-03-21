package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.code_block.value.LLVMFunctionType

data class FunctionIRType(
    val argumentTypes: List<IRType>,
    val outputType: IRType
) : IRType {
    override fun isSubtypeOf(other: IRType) = other == this

    override fun toString() = argumentTypes.joinToString(prefix = "(", postfix = ")") + " -> " + outputType

    override fun toLLVM() = LLVMFunctionType(
        argumentTypes.map { it.toLLVM() },
        outputType.toLLVM()
    )
}