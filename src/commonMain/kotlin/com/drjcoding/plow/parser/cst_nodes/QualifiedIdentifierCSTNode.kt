package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.project.ast.managers.Scope
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

    fun toAST() = QualifiedIdentifierASTNode(
        namespaces.map { it.name.token.text },
        name.token.text,
        this
    )

    fun toScope() = Scope(namespaces.map { it.name.token.text } + name.token.text)
}

/**
 * A namespace in a [QualifiedIdentifierCSTNode]. (ie. the `foo::` in `foo::bar::baz`).
 */
data class QINamespaceCSTNode(val name: TokenCSTNode, val doubleColon: TokenCSTNode) : CSTNode() {
    override val range: SourceFileRange = (name.range + doubleColon.range)
}