package com.drjcoding.plow.llvm.code_block

class LLVMFunctionBody(
    val sections: List<LLVMSection>
) {
    fun toIRCode(): String =
        sections.joinToString(separator = "\n") { it.toIRCode() }
}
