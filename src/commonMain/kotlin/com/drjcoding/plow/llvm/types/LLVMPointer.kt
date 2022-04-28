package com.drjcoding.plow.llvm.types

class LLVMPointer(val toType: LLVMType) : LLVMType {
    override fun toIRCode() = toType.toIRCode() + "*"
}