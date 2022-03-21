package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.mangle
import com.drjcoding.plow.llvm.types.LLVMNamedType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class NamedIRType(val scope: Scope, val name: SourceString) : IRType {
    override fun isSubtypeOf(other: IRType) = other == this

    override fun toString() = scope.toString() + "::" + name.toUnderlyingString()

    override fun toLLVM() = LLVMNamedType(
        mangle(scope, name)
    )
}