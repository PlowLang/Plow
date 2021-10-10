package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.StatementWithTerminatorCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType

/**
 * parses a [CodeBlockCSTNode] assuming we know one exists.
 */
internal fun parseCodeBlock(ts: LexTokenStream): CodeBlockCSTNode {
    val lCurly = ts.popNSTokenCSTNode().assertType(LexTokenType.L_CURLY)

    val statements: MutableList<StatementWithTerminatorCSTNode> = mutableListOf()
    var separator = true
    while (separator) {
        val possibleStatement = parseStatement(ts) ?: break
        val possibleSeparator = parseStatementSeparator(ts)
        separator = possibleSeparator != null
        statements.add(StatementWithTerminatorCSTNode(possibleStatement, possibleSeparator))
    }

    val rCurly = ts.popNSTokenCSTNode().expectType(LexTokenType.R_CURLY)
    return CodeBlockCSTNode(lCurly, statements, rCurly)
}

/**
 * Returns a [TokenCSTNode] if the next token is a newline or semicolon otherwise null.
 */
fun parseStatementSeparator(ts: LexTokenStream): TokenCSTNode? {
    var skipCount = 0
    while (true) {
        val token = ts.peek(skipCount)
        when {
            ts.isExhaustedAhead(skipCount) -> return null
            token.type == LexTokenType.SEMICOLON -> break
            token.type == LexTokenType.NEWLINE -> break
            token.isSkipable -> { }
            else -> return null
        }
        skipCount += 1
    }

    val skipped = (0 until skipCount).map { ts.pop() }
    val token = ts.pop()
    return TokenCSTNode(token, skipped)
}
