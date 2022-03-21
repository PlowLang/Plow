package com.drjcoding.plow.llvm.types

import com.drjcoding.plow.source_abstractions.SourceString

class LLVMNamedType(
    val name: SourceString
) : LLVMType {
    override fun toIRCode() = name
}