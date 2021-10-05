package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ParenthesizedExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parse a parenthesized expression assuming we know one exists.
 *
 * `parenExpression ::= L_PAREN expression R_PAREN
 */
internal fun parseParenExpression(ts: LexTokenStream): ParenthesizedExpressionCSTNode {
    val lParen = ts.popNSTokenCSTNode().assertType(LexTokenType.L_PAREN)
    val expression = parseExpression(ts) ?: throw ExpectedExpressionError(lParen.range)
    val rParen = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_PAREN)
    return ParenthesizedExpressionCSTNode(lParen, expression, rParen)
}