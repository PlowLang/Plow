package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class FunctionCallASTNode(
    val function: ExpressionASTNode,
    val arguments: List<ExpressionASTNode>,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()