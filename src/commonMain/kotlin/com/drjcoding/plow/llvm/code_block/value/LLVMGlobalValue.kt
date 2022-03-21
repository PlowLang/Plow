package com.drjcoding.plow.llvm.code_block.value

import com.drjcoding.plow.llvm.types.LLVMType
import com.drjcoding.plow.source_abstractions.SourceString

class LLVMGlobalValue(
    val name: SourceString,
    override val type: LLVMType,
) : LLVMValue {
    override fun toIRCode() = "@$name"
}