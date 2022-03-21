package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.types.LLVMType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.toSourceString

interface IRType {
    fun isSubtypeOf(other: IRType): Boolean
    fun toLLVM(): LLVMType
}

object StandardTypes {
    val STD_SCOPE = Scope(listOf("std".toSourceString()))

    val INT_IR_TYPE = NamedIRType(STD_SCOPE, "Int".toSourceString())
    val BOOLEAN_IR_TYPE = NamedIRType(STD_SCOPE, "Bool".toSourceString())
}