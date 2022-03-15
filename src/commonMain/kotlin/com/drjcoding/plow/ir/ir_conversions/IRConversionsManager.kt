package com.drjcoding.plow.ir.ir_conversions

import com.drjcoding.plow.ir.function.IRFunctionImplementation
import com.drjcoding.plow.ir.globals.IRGlobal

class DuplicateIRFunctionImplementation(
    val global: IRGlobal,
    val implementation1: IRFunctionImplementation,
    val implementation2: IRFunctionImplementation
) : Exception("Multiple implementations registered for the global $global.")

class IRConversionsManager {
    private val functions: MutableMap<IRGlobal, IRFunctionImplementation> = mutableMapOf()

    fun addIRFunction(irGlobal: IRGlobal, function: IRFunctionImplementation) {
        if (functions.containsKey(irGlobal)) throw DuplicateIRFunctionImplementation(
            irGlobal,
            functions[irGlobal]!!,
            function
        )

        functions[irGlobal] = function
    }
}