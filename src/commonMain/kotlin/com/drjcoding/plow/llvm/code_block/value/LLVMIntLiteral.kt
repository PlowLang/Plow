package com.drjcoding.plow.llvm.code_block.value

import com.drjcoding.plow.llvm.types.LLVMType

data class LLVMIntLiteral(
    val int: Int,
    override val type: LLVMType
) : LLVMValue {
    override fun toIRCode() = int.toString()
}