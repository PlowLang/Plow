package com.drjcoding.plow.llvm.code_block.value

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.types.LLVMType

class LLVMStackLocalValue(
    val id: LLVMLocalID,
    override val type: LLVMType
) : LLVMValue {
    override fun toIRCode() = TODO()
}