package com.drjcoding.plow.llvm.types

object LLVMVoid : LLVMType {
    override fun toIRCode() = "void"
}