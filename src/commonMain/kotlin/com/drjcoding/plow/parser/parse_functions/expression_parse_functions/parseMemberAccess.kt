package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.MemberAccessCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parses a member access assuming one has already been verified to exist.
 *
 * `memberAccess ::= expression DOT IDENTIFIER`
 */
fun parseMemberAccess(ts: LexTokenStream, currentExp: ExpressionCSTNode): MemberAccessCSTNode {
    val dot = ts.popNSTokenCSTNode().assertType(LexTokenType.PERIOD)
    val name = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)
    return MemberAccessCSTNode(currentExp, dot, name)
}