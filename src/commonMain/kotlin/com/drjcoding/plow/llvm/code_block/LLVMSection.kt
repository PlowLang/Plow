package com.drjcoding.plow.llvm.code_block

import com.drjcoding.plow.llvm.code_block.statement.LLVMStatement

class LLVMSection(
    val id: LLVMLocalID,
    val statements: List<LLVMStatement>
) {
    fun toIRCode(): String {
        statements.forEach(::println)
        return "${id.toIRCode()}:\n" + statements.joinToString(separator = "\n") { it.toIRCode() }
    }
}