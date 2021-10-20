package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class IfExpressionASTNode(
    val condition: ExpressionASTNode,
    val body: CodeBlockASTNode,
    val elseBody: CodeBlockASTNode?,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()
