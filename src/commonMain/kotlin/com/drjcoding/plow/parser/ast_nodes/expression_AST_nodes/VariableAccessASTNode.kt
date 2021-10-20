package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class VariableAccessASTNode(
    val name: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()