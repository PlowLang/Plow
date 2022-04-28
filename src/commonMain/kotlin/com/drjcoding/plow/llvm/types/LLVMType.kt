package com.drjcoding.plow.llvm.types

interface LLVMType {
    fun toIRCode(): String
}