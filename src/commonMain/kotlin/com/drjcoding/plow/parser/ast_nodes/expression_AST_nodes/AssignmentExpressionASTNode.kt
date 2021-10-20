package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class AssignmentExpressionASTNode(
    val assignTo: ExpressionASTNode,
    val value: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()