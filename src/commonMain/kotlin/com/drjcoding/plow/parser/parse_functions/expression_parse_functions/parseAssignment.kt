package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.AssignmentExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses an assignment assuming we know one exists.
 */
fun parseAssignment(ts: LexTokenStream, lhs: ExpressionCSTNode): AssignmentExpressionCSTNode {
    val assignOp = ts.popNSTokenCSTNode().assertType(LexTokenType.ASSIGN)
    val rhs = parseExpression(ts) ?: throw ExpectedExpressionError(assignOp.range)
    return AssignmentExpressionCSTNode(lhs, assignOp, rhs)
}