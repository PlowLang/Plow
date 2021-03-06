package com.drjcoding.plow.parser.cst_nodes.type_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.NamedTypeASTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode

/**
 * A regular named type (ex `foo::bar`).
 */
data class NamedTypeCSTNode(val name: QualifiedIdentifierCSTNode) : TypeCSTNode() {
    override val range = name.range

    override fun toAST() = NamedTypeASTNode(name.toAST(), this)
}