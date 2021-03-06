package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BinaryOpCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BindingPower
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses a binary operation assuming one has already been verified to exist.
 *
 * `binaryOp ::= expression OP expression`
 */
internal fun parseBinaryOp(
    ts: LexTokenStream,
    currentExp: ExpressionCSTNode,
    tightestBindingPower: BindingPower
): BinaryOpCSTNode {
    val op = ts.popNSTokenCSTNode()
    val right = parseSubExpression(
        ts,
        tightestBindingPower tightestOf BindingPower.fromOp(op.token)
    ) ?: throw ExpectedExpressionError(op.range)
    return BinaryOpCSTNode(currentExp, op, right)
}
