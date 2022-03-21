package com.drjcoding.plow.ir.function

import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRLocalVariable
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.llvm.LLVMFunction
import com.drjcoding.plow.llvm.mangle
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

class IRFunctionImplementation(
    val scope: Scope,
    val name: SourceString,
    val returnType: IRType,
    val arguments: List<IRLocalVariable>,
    val implementation: IRCodeBlock
) {
    fun toLLVM(): LLVMFunction = LLVMFunction(
        mangle(scope, name),
        returnType.toLLVM(),
        arguments.map { it.type.toLLVM() },
        implementation.toLLVMFunctionBody(arguments)
    )

}