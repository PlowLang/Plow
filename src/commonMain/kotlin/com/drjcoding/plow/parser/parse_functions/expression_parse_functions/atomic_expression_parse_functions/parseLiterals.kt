package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.FloatLiteralCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.IntLiteralCSTNode
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses a int literal assuming we know one exists.
 */
internal fun parseIntLiteral(ts: LexTokenStream): IntLiteralCSTNode =
    IntLiteralCSTNode(ts.popNSTokenCSTNode())

/**
 * Parses a float literal assuming we know one exists.
 */
internal fun parseFloatLiteral(ts: LexTokenStream): FloatLiteralCSTNode =
    FloatLiteralCSTNode(ts.popNSTokenCSTNode())