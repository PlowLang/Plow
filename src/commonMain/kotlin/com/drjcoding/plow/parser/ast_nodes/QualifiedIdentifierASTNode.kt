package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class QualifiedIdentifierASTNode(
    val namespaces: List<SourceString>,
    val name: SourceString,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    /**
     * A [FullyQualifiedLocation] made up of [namespaces].
     */
    val fullyQualifiedLocation = FullyQualifiedLocation(namespaces)

    /**
     * A [FullyQualifiedLocation] made up of [namespaces] and [name] at the end.
     */
    val fullyQualifiedLocationWithName = FullyQualifiedLocation(namespaces.plus(name))

    override fun toString() = namespaces.joinToString(separator = "::") + "::" + name.toUnderlyingString()
}
