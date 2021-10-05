package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * Thrown when parsing invariants are broken.
 */
class ParseAssertionUnexpectedToken(found: TokenCSTNode, expectedType: LexTokenType) :
    Exception("Expected $expectedType, instead found $found")

/**
 * Use this to assert that a token should have a type due to known invariants not to find syntax errors. Throws
 * [ParseAssertionUnexpectedToken] if the wrong type is found.
 */
fun TokenCSTNode.assertType(type: LexTokenType): TokenCSTNode {
    if (this.token.type != type) throw ParseAssertionUnexpectedToken(this, type)
    return this
}