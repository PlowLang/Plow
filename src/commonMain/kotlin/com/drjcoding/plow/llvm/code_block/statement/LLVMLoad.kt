package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.types.LLVMPointer
import com.drjcoding.plow.llvm.types.LLVMType

data class LLVMLoad(
    val to: LLVMLocalID,
    val from: LLVMLocalID,
    val type: LLVMType
) : LLVMStatement {
    override fun toIRCode() =
        "%${to.toIRCode()} = load ${type.toIRCode()}, ${LLVMPointer(type).toIRCode()} %${from.toIRCode()}"
}