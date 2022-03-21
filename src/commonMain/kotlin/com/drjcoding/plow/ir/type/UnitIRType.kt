package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.types.LLVMVoid

object UnitIRType : IRType {
    override fun isSubtypeOf(other: IRType) = other == UnitIRType

    override fun toString() = "Unit"

    override fun toLLVM() = LLVMVoid
}