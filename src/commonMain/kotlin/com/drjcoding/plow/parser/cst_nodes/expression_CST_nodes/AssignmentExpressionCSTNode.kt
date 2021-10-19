package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.AssignmentExpressionASTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An assignment (ex `foo = bar`). Note that although not all expressions can be assigned to we allow it here for ease
 * of parsing. It will be checked later.
 */
data class AssignmentExpressionCSTNode(
    val assignTo: ExpressionCSTNode,
    val assignOp: TokenCSTNode,
    val value: ExpressionCSTNode
) : ExpressionCSTNode() {
    override val range = assignTo.range + value.range

    override fun toAST() = AssignmentExpressionASTNode(
        assignTo.toAST(),
        value.toAST(),
        this
    )
}