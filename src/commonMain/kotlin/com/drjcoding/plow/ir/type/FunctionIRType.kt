package com.drjcoding.plow.ir.type

data class FunctionIRType(
    val argumentTypes: List<IRType>,
    val outputType: IRType
) : IRType