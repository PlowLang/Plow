package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An expression inside parenthesis. (Ex. `(foo)`)
 *
 * @property lParen the first, left, parenthesis
 * @property expression the inner expression
 * @property rParen the second, right, parenthesis
 */
data class ParenthesizedExpressionCSTNode(
    val lParen: TokenCSTNode,
    val expression: ExpressionCSTNode,
    val rParen: TokenCSTNode
): ExpressionCSTNode() {
    override val range = lParen.range + rParen.range

    override fun toAST() = expression.toAST()
}