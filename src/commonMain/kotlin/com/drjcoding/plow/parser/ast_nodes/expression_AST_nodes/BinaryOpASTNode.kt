package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class BinaryOpASTNode(
    val left: ExpressionASTNode,
    val op: SourceString,
    val right: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()