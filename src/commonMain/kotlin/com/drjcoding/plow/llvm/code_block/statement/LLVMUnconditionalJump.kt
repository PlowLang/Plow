package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID

data class LLVMUnconditionalJump(
    val label: LLVMLocalID
) : LLVMStatement {
    override fun toIRCode() =
        "br label %${label.toIRCode()}"
}