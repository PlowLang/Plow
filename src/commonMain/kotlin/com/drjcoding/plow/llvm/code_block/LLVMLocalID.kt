package com.drjcoding.plow.llvm.code_block

data class LLVMLocalID(
    val id: Int
) {
    fun toIRCode() = id.toString()
}