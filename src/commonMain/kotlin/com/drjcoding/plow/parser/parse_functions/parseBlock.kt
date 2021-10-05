package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType

/**
 * parses a [CodeBlockCSTNode] assuming we know one exists.
 */
internal fun parseCodeBlock(ts: LexTokenStream): CodeBlockCSTNode {
    val lCurly = ts.popNSTokenCSTNode().assertType(LexTokenType.L_CURLY)
    // TODO - finish parsing
    val rCurly = ts.popNSTokenCSTNode().expectType(LexTokenType.R_CURLY)
    return CodeBlockCSTNode(lCurly, listOf(), rCurly)
}