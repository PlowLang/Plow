package com.drjcoding.plow.llvm.types

import com.drjcoding.plow.source_abstractions.SourceString

interface LLVMType {
    fun toIRCode(): SourceString
}