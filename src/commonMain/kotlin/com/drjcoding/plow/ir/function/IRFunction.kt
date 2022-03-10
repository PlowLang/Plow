package com.drjcoding.plow.ir.function

import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.globals.IRGlobal
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

class IRFunction(
    override val scope: Scope,
    override val name: SourceString,
    override val type: IRType,
    val implementation: IRCodeBlock
) : IRGlobal