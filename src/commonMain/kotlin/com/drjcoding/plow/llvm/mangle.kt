package com.drjcoding.plow.llvm

import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toSourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

fun mangle(scope: Scope, name: SourceString): SourceString {
    val prefix = "_plow_"
    val scopes = scope.names
        .map { it.toUnderlyingString() }
        .map { it.length.toString() + it }
        .joinToString(separator = "_")
    val nameStr = name.toUnderlyingString().let { it.length.toString() + it }

    return (prefix + scopes + "__" + nameStr).toSourceString()
}