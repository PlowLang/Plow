package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.source_abstractions.toUnderlyingString

/**
 * A binary op expression. (Ex. `foo + bar`)
 *
 * @property left the left expression
 * @property op the operator
 * @property right the right expression
 */
data class BinaryOpCSTNode(
    val left: ExpressionCSTNode,
    val op: TokenCSTNode,
    val right: ExpressionCSTNode
): ExpressionCSTNode() {
    override val range = left.range + right.range

    override val bindingPower = BindingPower.fromOp(op.token.text.toUnderlyingString())
}

class InvalidBinaryOperatorError(op: String): Exception("Invalid binary operator $op")