package com.drjcoding.plow.llvm.types

data class LLVMFunctionType(
    val argTypes: List<LLVMType>,
    val returnType: LLVMType
) : LLVMType {
    override fun toIRCode(): String {
        //TODO
        return "???"
    }
}