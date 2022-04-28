package com.drjcoding.plow.ir.function

import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRLocalVariable
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.llvm.LLVMFunction
import com.drjcoding.plow.llvm.code_block.LLVMFunctionBody
import com.drjcoding.plow.llvm.mangle
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

class IRFunctionImplementation(
    val scope: Scope?, // TODO sloppy
    val name: SourceString,
    val returnType: IRType,
    val arguments: List<IRLocalVariable>,
    val implementation: IRFunctionBody,
    val noMangle: Boolean,
) {
    fun toLLVM(): LLVMFunction = LLVMFunction(
        if (!noMangle) mangle(scope!!, name) else name,
        returnType.toLLVM(),
        arguments.map { it.type.toLLVM() },
        implementation.toLLVMFunctionBody(arguments, returnType)
    )
}

sealed class IRFunctionBody {
    abstract fun toLLVMFunctionBody(arguments: List<IRLocalVariable>, returnType: IRType): LLVMFunctionBody

    class BlockBody(val body: IRCodeBlock) : IRFunctionBody() {
        override fun toLLVMFunctionBody(arguments: List<IRLocalVariable>, returnType: IRType): LLVMFunctionBody =
            body.toLLVMFunctionBody(arguments, returnType)
    }

    object ExternBody : IRFunctionBody() {
        override fun toLLVMFunctionBody(arguments: List<IRLocalVariable>, returnType: IRType): LLVMFunctionBody =
            LLVMFunctionBody.Extern
    }
}