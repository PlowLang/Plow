package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.value.LLVMValue

data class LLVMRet(
    val value: LLVMValue?
) : LLVMStatement {
    override fun toIRCode() =
        when (value) {
            null -> "ret void"
            else -> "ret ${value.type.toIRCode()} ${value.toIRCode()}"
        }
}