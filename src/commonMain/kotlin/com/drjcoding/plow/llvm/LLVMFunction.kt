package com.drjcoding.plow.llvm

import com.drjcoding.plow.llvm.code_block.LLVMFunctionBody
import com.drjcoding.plow.llvm.types.LLVMType
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

class LLVMFunction(
    val name: SourceString,
    val returnType: LLVMType,
    val argumentTypes: List<LLVMType>,
    val codeBlock: LLVMFunctionBody
) {
    fun toIRCode(): String {
        val argumentsIR = argumentTypes.joinToString(separator = ", ") { it.toIRCode().toUnderlyingString() }
        val codeIR = codeBlock.toIRCode().replace("^".toRegex(), "    ")
        return """
            define @${returnType.toIRCode()} $name($argumentsIR) nounwind {
            $codeIR
            }
        """.trimIndent()
    }
}