package com.drjcoding.plow.llvm.code_block

import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

sealed class LLVMFunctionBody {
    abstract fun toIRCode(): Pair<String, String>

    class BlockBody(val sections: List<LLVMSection>) : LLVMFunctionBody() {
        override fun toIRCode() =
            "define" to sections.joinToString(separator = "\n", prefix = "{\n", postfix = "\n}") { it.toIRCode() }
    }

    class ExternCode(val llvmCode: SourceString) : LLVMFunctionBody() {
        override fun toIRCode() = "define" to llvmCode.toUnderlyingString()
    }

    object Extern : LLVMFunctionBody() {
        override fun toIRCode() = "declare" to ""
    }
}
