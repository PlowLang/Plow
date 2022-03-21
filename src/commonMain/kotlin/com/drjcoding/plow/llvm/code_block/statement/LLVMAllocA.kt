package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.types.LLVMType

class LLVMAllocA(
    val to: LLVMLocalID,
    val type: LLVMType
) : LLVMStatement {
    override fun toIRCode(): String = "%${to.toIRCode()} = alloca ${type.toIRCode()}"
}