package com.drjcoding.plow.parser.ast_nodes.type_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class NamedTypeASTNode(
    val name: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : TypeASTNode()