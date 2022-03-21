package com.drjcoding.plow.llvm.types

import com.drjcoding.plow.source_abstractions.toSourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

class LLVMPointer(val toType: LLVMType) : LLVMType {
    override fun toIRCode() = (toType.toIRCode().toUnderlyingString() + "*").toSourceString()
}