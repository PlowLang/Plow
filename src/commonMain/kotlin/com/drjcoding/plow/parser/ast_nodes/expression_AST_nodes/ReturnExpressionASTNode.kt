package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class ReturnExpressionASTNode(
    val expression: ExpressionASTNode?,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()