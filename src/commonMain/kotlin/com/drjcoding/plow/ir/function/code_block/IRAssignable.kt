package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.type.IRType

sealed class IRAssignable(val type: IRType) {
    data class LocalVariable(val variable: IRLocalVariable) : IRAssignable(variable.type)

//    class Global(val global: IRGlobal) : IRAssignable(global.type)
}