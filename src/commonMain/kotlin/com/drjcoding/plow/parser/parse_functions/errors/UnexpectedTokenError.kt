package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Thrown any time an unexpected token is found that means a syntax error.
 */
class UnexpectedTokenError(
    foundToken: TokenCSTNode?,
    expectedType: LexTokenType
): PlowError(
    "unexpected token type",
    PlowIssueInfo(foundToken?.range?.toPlowIssueTextRange(), "Expected a token of type $expectedType but instead found $foundToken")
)

/**
 * Use this function to assert that syntactically a token should have a certain type. Throws an [UnexpectedTokenError]
 * if the expected type isn't found.
 */
fun TokenCSTNode?.expectType(type: LexTokenType): TokenCSTNode {
    if (this?.token?.type != type) throw UnexpectedTokenError(this, type)
    return this
}