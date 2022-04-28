package com.drjcoding.plow.llvm.code_block

sealed class LLVMFunctionBody {
    abstract fun toIRCode(): Pair<String, String>

    class BlockBody(
        val sections: List<LLVMSection>
    ) : LLVMFunctionBody() {
        override fun toIRCode() =
            "define" to sections.joinToString(separator = "\n", prefix = "{\n", postfix = "\n}") { it.toIRCode() }
    }

    object Extern : LLVMFunctionBody() {
        override fun toIRCode() = "declare" to ""
    }
}
