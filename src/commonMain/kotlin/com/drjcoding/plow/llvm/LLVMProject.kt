package com.drjcoding.plow.llvm

class LLVMProject(
    val functions: List<LLVMFunction>
) {
    fun toIRCode(): String {
        var code = """
        target datalayout = "e-m:o-p:24:8-p1:16:8-p2:16:8-i16:8-i24:8-i32:8-i48:8-i64:8-i96:8-f32:8-f64:8-a:8-n8:16:24-S8"
        target triple = "ez80"
        
        """.trimIndent()
        code += functions.joinToString(separator = "\n\n", transform = LLVMFunction::toIRCode)
        return code
    }
}