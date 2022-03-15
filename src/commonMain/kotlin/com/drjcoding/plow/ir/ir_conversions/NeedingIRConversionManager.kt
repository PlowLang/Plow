package com.drjcoding.plow.ir.ir_conversions

class NeedingIRConversionManager {
    private val functionsNeedingConversion: MutableSet<IRFunctionProducer> = mutableSetOf()

    fun registerFunctionNeedingConversion(function: IRFunctionProducer) {
        functionsNeedingConversion.add(function)
    }

    fun functionsNeedingConversionIterator() = functionsNeedingConversion.iterator()
}