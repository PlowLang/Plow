package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * A qualified identifier (ie. `foo::bar::baz`)
 *
 * @field namespaces the names of the namespaces before the identifier in order
 * @field name the identifier itself
 */
data class QualifiedIdentifierCSTNode(
    val namespaces: List<QINamespaceCSTNode>,
    val name: TokenCSTNode,
) : CSTNode() {
    override val range: SourceFileRange = (namespaces.firstOrNull() ?: name).range
}

/**
 * A namespace in a [QualifiedIdentifierCSTNode]. (ie. the `foo::` in `foo::bar::baz`).
 */
data class QINamespaceCSTNode(val name: TokenCSTNode, val doubleColon: TokenCSTNode) : CSTNode() {
    override val range: SourceFileRange = (name.range + doubleColon.range)
}