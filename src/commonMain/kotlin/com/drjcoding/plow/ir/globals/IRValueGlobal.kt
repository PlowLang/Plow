package com.drjcoding.plow.ir.globals

import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class IRValueGlobal(
    val scope: Scope,
    val name: SourceString,
    val type: IRType
) : IRGlobal