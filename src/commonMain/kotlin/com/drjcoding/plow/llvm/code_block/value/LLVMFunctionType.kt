package com.drjcoding.plow.llvm.code_block.value

import com.drjcoding.plow.llvm.types.LLVMType
import com.drjcoding.plow.source_abstractions.SourceString

class LLVMFunctionType(
    val argTypes: List<LLVMType>,
    val returnType: LLVMType
) : LLVMType {
    override fun toIRCode(): SourceString {
        TODO("Not yet implemented")
    }
}