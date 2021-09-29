package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * A return expression.
 *
 * @property returnToken the return keyword
 * @property expression the expression returned or null if no expression is
 */
data class ReturnExpressionCSTNode(
    val returnToken: TokenCSTNode,
    val expression: ExpressionCSTNode?
): ExpressionCSTNode() {
    override val range = if (expression != null) returnToken.range + expression.range else returnToken.range
}
