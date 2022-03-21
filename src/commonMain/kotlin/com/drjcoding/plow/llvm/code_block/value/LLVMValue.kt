package com.drjcoding.plow.llvm.code_block.value

import com.drjcoding.plow.llvm.types.LLVMType

interface LLVMValue {
    val type: LLVMType

    fun toIRCode(): String
}