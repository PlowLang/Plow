package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexToken
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.TokenStreamAccessedAfterExhaustedException
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * Returns the [TokenCSTNode] representation of the next non-skipable token or null if there isn't one.
 */
fun LexTokenStream.safePeekNSTokenCSTNode(): TokenCSTNode? {
    val skippedTokens: MutableList<LexToken> = mutableListOf()
    var tokensAhead = 0
    while (safePeek(tokensAhead)?.isSkipable == true) {
        skippedTokens.add(safePeek(tokensAhead++)!!)
    }
    val token = safePeekNS()
    return if (token == null) {
        null
    } else {
        TokenCSTNode(token, skippedTokens)
    }
}

/**
 * Returns the [TokenCSTNode] representation of the non-skipable token [ahead] ahead or null if there isn't one.
 */
fun LexTokenStream.safePeekNSTokenCSTNode(ahead: Int): TokenCSTNode? {
    var latest: TokenCSTNode? = null
    var tokensAhead = 0

    repeat(ahead + 1) {
        val skippedTokens: MutableList<LexToken> = mutableListOf()
        while (safePeek(tokensAhead)?.isSkipable == true) {
            skippedTokens.add(safePeek(tokensAhead++)!!)
        }
        val token = safePeek(tokensAhead++)
        latest = if (token == null) {
            null
        } else {
            TokenCSTNode(token, skippedTokens)
        }
    }

    return latest
}

/**
 * Returns the [TokenCSTNode] representation of the next non-skipable token or throws
 * [TokenStreamAccessedAfterExhaustedException] if there isn't one.
 */
fun LexTokenStream.peekNSTokenCSTNode(): TokenCSTNode =
    this.safePeekNSTokenCSTNode() ?: throw TokenStreamAccessedAfterExhaustedException()

/**
 * Returns the [TokenCSTNode] representation of the non-skipable token [ahead] ahead or throws
 * [TokenStreamAccessedAfterExhaustedException] if there isn't one.
 */
fun LexTokenStream.peekNSTokenCSTNode(ahead: Int): TokenCSTNode =
    this.safePeekNSTokenCSTNode(ahead) ?: throw TokenStreamAccessedAfterExhaustedException()

/**
 * Returns the [TokenCSTNode] representation of the next non-skipable token or null if there isn't one and advances the
 * stream beyond the token.
 */
fun LexTokenStream.safePopNSTokenCSTNode(): TokenCSTNode? {
    val skippedTokens: MutableList<LexToken> = mutableListOf()
    var tokensAhead = 0
    while (safePeek(tokensAhead)?.isSkipable == true) {
        skippedTokens.add(safePeek(tokensAhead++)!!)
    }
    val token = safePeekNS()
    return if (token == null) {
        null
    } else {
        TokenCSTNode(safePopNS()!!, skippedTokens)
    }
}

/**
 * Returns the [TokenCSTNode] representation of the next non-skipable token or throws
 * [TokenStreamAccessedAfterExhaustedException] if there isn't one and advances the stream beyond the token.
 */
fun LexTokenStream.popNSTokenCSTNode(): TokenCSTNode =
    this.safePopNSTokenCSTNode() ?: throw TokenStreamAccessedAfterExhaustedException()