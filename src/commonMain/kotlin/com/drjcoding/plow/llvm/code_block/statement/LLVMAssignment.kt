package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMValue
import com.drjcoding.plow.llvm.types.LLVMPointer

data class LLVMAssignment(
    val to: LLVMLocalID,
    val value: LLVMValue
) : LLVMStatement {
    override fun toIRCode() =
        "store ${value.type.toIRCode()} ${value.toIRCode()}, ${LLVMPointer(value.type).toIRCode()} %${to.toIRCode()}"
}