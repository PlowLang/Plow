package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.types.LLVMFunctionType

data class FunctionIRType(
    val argumentTypes: List<IRType>,
    val returnType: IRType
) : IRType {
    override fun isSubtypeOf(other: IRType) = other == this

    override fun toString() = argumentTypes.joinToString(prefix = "(", postfix = ")") + " -> " + returnType

    override fun toLLVM() = LLVMFunctionType(
        argumentTypes.map { it.toLLVM() },
        returnType.toLLVM()
    )
}