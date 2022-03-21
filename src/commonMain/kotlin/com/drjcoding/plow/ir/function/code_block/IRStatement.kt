package com.drjcoding.plow.ir.function.code_block

sealed class IRStatement {
    class Label : IRStatement()

    class Jump(val label: Label, val condition: IRValue?, val inverted: Boolean) : IRStatement()

    class Assignment(val to: IRAssignable, val value: IRValue) : IRStatement()

    class FunctionCall(val to: IRAssignable, val function: IRValue, val arguments: List<IRValue>) : IRStatement()

    class Return(val value: IRValue) : IRStatement()
}
