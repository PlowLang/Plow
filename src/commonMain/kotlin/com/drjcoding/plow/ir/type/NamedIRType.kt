package com.drjcoding.plow.ir.type

import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class NamedIRType(val scope: Scope, val name: SourceString) : IRType {
    override fun isSubtypeOf(other: IRType) = other == this
}