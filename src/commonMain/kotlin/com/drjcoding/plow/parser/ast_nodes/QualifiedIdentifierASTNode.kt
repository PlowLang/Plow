package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class QualifiedIdentifierASTNode(
    val namespaces: List<SourceString>,
    val name: SourceString,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    fun prettyPrint(): String =
        if (namespaces.isEmpty()) name.toUnderlyingString() else
        namespaces.joinToString(separator = "::", postfix = "::") { it.toUnderlyingString() } + name.toUnderlyingString()
}
