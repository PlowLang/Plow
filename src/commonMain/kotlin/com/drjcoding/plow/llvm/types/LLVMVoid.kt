package com.drjcoding.plow.llvm.types

import com.drjcoding.plow.source_abstractions.toSourceString

object LLVMVoid : LLVMType {
    override fun toIRCode() = "void".toSourceString()
}