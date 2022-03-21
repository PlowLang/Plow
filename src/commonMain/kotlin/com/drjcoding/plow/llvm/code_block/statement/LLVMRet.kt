package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.value.LLVMValue

class LLVMRet(
    val value: LLVMValue?
) : LLVMStatement {
    override fun toIRCode() =
        when (value) {
            null -> "ret void"
            else -> "red ${value.type.toIRCode()} ${value.toIRCode()}"
        }
}