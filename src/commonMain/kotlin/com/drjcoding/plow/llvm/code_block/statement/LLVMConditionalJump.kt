package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMValue

class LLVMConditionalJump(
    val condition: LLVMValue,
    val ifTrue: LLVMLocalID,
    val ifFalse: LLVMLocalID
) : LLVMStatement {
    override fun toIRCode() =
        "br i1 ${condition.toIRCode()}, label %${ifTrue.toIRCode()}, label %${ifFalse.toIRCode()}"
}