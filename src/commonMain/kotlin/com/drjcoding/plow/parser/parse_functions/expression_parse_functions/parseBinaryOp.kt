package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BinaryOpCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BindingPower
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.source_abstractions.toUnderlyingString

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
    val op = ts.popNSTokenCSTNode().assertType(LexTokenType.OPERATOR)
    val right = parseSubExpression(
        ts,
        tightestBindingPower tightestOf BindingPower.fromOp(op.token.text.toUnderlyingString())
    ) ?: throw ExpectedExpressionError(op.range)
    return BinaryOpCSTNode(currentExp, op, right)
}
