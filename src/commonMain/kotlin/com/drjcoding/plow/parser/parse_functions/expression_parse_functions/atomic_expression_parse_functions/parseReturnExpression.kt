package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ReturnExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.assertType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses a return expression assuming we already know there is a return expression.
 *
 * `returnExpression ::= RETURN expression?`
 */
internal fun parseReturnExpression(ts: LexTokenStream): ReturnExpressionCSTNode {
    val returnToken = ts.popNSTokenCSTNode().assertType(LexTokenType.RETURN)
    val expression = parseExpression(ts)
    return ReturnExpressionCSTNode(returnToken, expression)
}