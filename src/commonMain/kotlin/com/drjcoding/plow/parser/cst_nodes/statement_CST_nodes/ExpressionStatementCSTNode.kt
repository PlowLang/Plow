package com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode

/**
 * A statement consisting of just an expression
 */
data class ExpressionStatementCSTNode(val expression: ExpressionCSTNode): StatementCSTNode() {
    override val range = expression.range
}