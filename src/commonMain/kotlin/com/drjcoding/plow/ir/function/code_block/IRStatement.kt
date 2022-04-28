package com.drjcoding.plow.ir.function.code_block

sealed class IRStatement {
    object Label : IRStatement()

    data class Jump(val label: Label, val condition: IRValue?, val inverted: Boolean) : IRStatement()

    data class Assignment(val to: IRAssignable, val value: IRValue) : IRStatement()

    data class FunctionCall(val to: IRAssignable, val function: IRValue, val arguments: List<IRValue>) : IRStatement()

    data class Return(val value: IRValue) : IRStatement()
}
