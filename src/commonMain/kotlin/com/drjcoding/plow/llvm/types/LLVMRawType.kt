package com.drjcoding.plow.llvm.types

import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

class LLVMRawType(
    val name: SourceString
) : LLVMType {
    override fun toIRCode() = name.toUnderlyingString()
}