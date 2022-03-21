package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMFunctionType
import com.drjcoding.plow.llvm.code_block.value.LLVMValue
import com.drjcoding.plow.source_abstractions.toUnderlyingString

class LLVMFunctionCall(
    val assignTo: LLVMLocalID,
    val function: LLVMValue,
    val args: List<LLVMValue>
) : LLVMStatement {
    override fun toIRCode(): String {
        val functionType = function.type as LLVMFunctionType
        val argsIR = args.joinToString { it.type.toIRCode().toUnderlyingString() + " " + it.toIRCode() }
        return "%${assignTo.toIRCode()} = call ${functionType.returnType.toIRCode()} ${function.toIRCode()}($argsIR)"
    }
}