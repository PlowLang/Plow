package com.drjcoding.plow.ir.type

object UnitIRType : IRType {
    override fun isSubtypeOf(other: IRType) = other == UnitIRType
}