package com.drjcoding.plow.ir.type

data class FunctionIRType(
    val argumentTypes: List<IRType>,
    val outputType: IRType
) : IRType {
    override fun isSubtypeOf(other: IRType) = other == this

    override fun toString() = argumentTypes.joinToString(prefix = "(", postfix = ")") + " -> " + outputType
}