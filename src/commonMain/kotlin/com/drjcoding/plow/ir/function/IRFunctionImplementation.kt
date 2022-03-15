package com.drjcoding.plow.ir.function

import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRLocalVariable
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

class IRFunctionImplementation(
    val scope: Scope,
    val name: SourceString,
    val type: IRType,
    val arguments: List<IRLocalVariable>,
    val implementation: IRCodeBlock
)