package com.drjcoding.plow.llvm.code_block.statement

interface LLVMStatement {
    fun toIRCode(): String
}