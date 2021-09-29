package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * A call to a function. (Ex. `foo(bar, baz)`)
 *
 * @property function the function being called
 * @property lParen the first, left, parenthesis
 * @property arguments the function arguments
 * @property rParen the second, right, parenthesis.
 */
data class FunctionCallCSTNode(
    val function: ExpressionCSTNode,
    val lParen: TokenCSTNode,
    val arguments: List<FunctionArgumentCSTNode>,
    val rParen: TokenCSTNode
): ExpressionCSTNode() {
    override val range = function.range + rParen.range
}

/**
 * An argument in a function call.
 *
 * @property value the argument
 * @property comma the comma after the argument
 */
data class FunctionArgumentCSTNode(val value: ExpressionCSTNode, val comma: TokenCSTNode?): CSTNode() {
    override val range = if (comma != null) value.range + comma.range else value.range
}