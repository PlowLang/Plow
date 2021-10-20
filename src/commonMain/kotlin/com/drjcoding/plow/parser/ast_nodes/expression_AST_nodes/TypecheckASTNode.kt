package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class TypecheckASTNode(
    val expression: ExpressionASTNode,
    val type: TypeASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()