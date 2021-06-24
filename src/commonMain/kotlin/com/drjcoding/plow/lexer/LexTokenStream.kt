package com.drjcoding.plow.lexer

/**
 * A stream of [LexToken]s to be used for parsing.
 */
class LexTokenStream(private val tokens: List<LexToken>) {
    private var currentToken = 0

    /**
     * Advances the stream [times] tokens.
     */
    private fun advanceStream(times: Int = 1) {
        currentToken += times
    }

    /**
     * Whether or not the stream has been exhausted.
     */
    val isExhausted: Boolean
        get() = currentToken >= tokens.size

    /**
     * Returns the next token in the stream without advancing the stream or null if the stream has been exhausted
     * ([isExhausted]).
     */
    fun safePeek(): LexToken? = if (isExhausted) null else tokens[currentToken]

    /**
     * Returns the next token in the stream without advancing the stream. If the stream has been exhausted
     * ([isExhausted]) then the function throws a [TokenStreamAccessedAfterExhaustedException].
     */
    fun peek(): LexToken = if (isExhausted) throw TokenStreamAccessedAfterExhaustedException() else tokens[currentToken]

    /**
     * Returns the next token in the stream and advance the stream or returns null if the stream has been exhausted
     * ([isExhausted]).
     */
    fun safePop(): LexToken? {
        val token = safePeek()
        if (token != null) {
            advanceStream()
        }
        return token
    }

    /**
     * Returns the next token in the stream and advances the stream. If the stream has been exhausted ([isExhausted])
     * then the function throws a TODO.
     */
    fun pop(): LexToken {
        val token = peek()
        advanceStream()
        return token
    }

}


/**
 * A [TokenStreamAccessedAfterExhaustedException] is thrown when a token of a [LexTokenStream] is accessed
 * after the stream has been exhausted ([LexTokenStream.isExhausted] is true).
 */
class TokenStreamAccessedAfterExhaustedException : Exception()


