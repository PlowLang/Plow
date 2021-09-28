package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.lexer.LexToken
import com.drjcoding.plow.source_abstractions.SourceFileRange

typealias Skipables = List<LexToken>

/**
 * A [CSTNode] containing a single [LexToken].
 *
 * @field token the [LexToken]
 */
data class TokenCSTNode(val token: LexToken, val leftwardSkipableTokens: Skipables) : CSTNode() {
    override val range: SourceFileRange = (leftwardSkipableTokens.firstOrNull() ?: token).range + token.range
}