package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMValue

class LLVMAssignment(
    val to: LLVMLocalID,
    val value: LLVMValue
): LLVMStatement {
    override fun toIRCode() =
        "store ${value.type.toIRCode()} ${value.toIRCode()}, ${value.type.toIRCode()}* %${to.toIRCode()}"
}