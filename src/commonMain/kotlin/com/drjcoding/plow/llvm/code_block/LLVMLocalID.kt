package com.drjcoding.plow.llvm.code_block

data class LLVMLocalID(
    val id: Int,
    val isArg: Boolean,
) {
    fun toIRCode() = (if (isArg) "" else "x") + id.toString()
}