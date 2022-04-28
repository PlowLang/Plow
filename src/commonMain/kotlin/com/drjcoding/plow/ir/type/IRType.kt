package com.drjcoding.plow.ir.type

import com.drjcoding.plow.llvm.types.LLVMType
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.toSourceString

interface IRType {
    fun isSubtypeOf(other: IRType): Boolean
    fun toLLVM(): LLVMType
}

object StandardTypes {
    // TODO change this to std
    val STD_SCOPE = Scope(listOf("file".toSourceString()))

    // TODO this is hacky
    val INT_IR_TYPE = ExternIRType(STD_SCOPE, "Int".toSourceString(), "i24".toSourceString())
    val BOOLEAN_IR_TYPE = NamedIRType(STD_SCOPE, "Bool".toSourceString())
}