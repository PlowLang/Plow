package com.drjcoding.plow.llvm.code_block.statement

import com.drjcoding.plow.llvm.code_block.LLVMLocalID
import com.drjcoding.plow.llvm.code_block.value.LLVMValue
import com.drjcoding.plow.llvm.types.LLVMFunctionType

data class LLVMFunctionCall(
    val assignTo: LLVMLocalID,
    val function: LLVMValue,
    val args: List<LLVMValue>
) : LLVMStatement {
    override fun toIRCode(): String {
        val functionType = function.type as LLVMFunctionType
        val argsIR = args.joinToString { it.type.toIRCode() + " " + it.toIRCode() }
        return "%${assignTo.toIRCode()} = call ${functionType.returnType.toIRCode()} ${function.toIRCode()}($argsIR)"
    }
}