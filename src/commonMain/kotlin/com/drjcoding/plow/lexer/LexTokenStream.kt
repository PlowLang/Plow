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
     * Whether the stream has been exhausted.
     */
    val isExhausted: Boolean
        get() = currentToken >= tokens.size

    /**
     * Whether the stream would be exhausted after advancing the stream [ahead] times.
     */
    fun isExhaustedAhead(ahead: Int) = currentToken + ahead >= tokens.size

    /**
     * Returns the next token in the stream without advancing the stream or null if the stream has been exhausted.
     * ([isExhausted]).
     */
    fun safePeek(): LexToken? = if (isExhausted) null else tokens[currentToken]

    /**
     * Returns the token [ahead] tokens in the stream (with zero being the current token) without advancing the stream
     * or null if the stream is exhausted that far ahead ([isExhaustedAhead]).
     */
    fun safePeek(ahead: Int): LexToken? = if (isExhaustedAhead(ahead)) null else tokens[currentToken + ahead]

    /**
     * Returns the next token in the stream that is not skipable or null if the stream has been exhausted or there is
     * no nonskipable token in the stream.
     */
    fun safePeekNS(): LexToken? {
        var ahead = 0
        while (safePeek(ahead)?.isSkipable == true) ahead++
        return safePeek(ahead)
    }

    /**
     * Returns the next token in the stream without advancing the stream. If the stream has been exhausted
     * ([isExhausted]) then the function throws a [TokenStreamAccessedAfterExhaustedException].
     */
    fun peek(): LexToken = if (isExhausted) throw TokenStreamAccessedAfterExhaustedException() else tokens[currentToken]

    /**
     * Returns the token [ahead] tokens in the stream (with zero being the current token) without advancing the stream.
     * If the stream is exhausted that far ahead ([isExhaustedAhead]) then the function throws a
     * [TokenStreamAccessedAfterExhaustedException].
     */
    fun peek(ahead: Int): LexToken =
        if (isExhaustedAhead(ahead)) throw TokenStreamAccessedAfterExhaustedException()
        else tokens[currentToken + ahead]

    /**
     * Returns the next token in the stream that is not skipable. If the stream has been exhausted or there is
     * no nonskipable token in the stream then the function throws a [TokenStreamAccessedAfterExhaustedException].
     */
    fun peekNS(): LexToken = safePeekNS() ?: throw TokenStreamAccessedAfterExhaustedException()

    /**
     * Returns the next token in the stream and advances the stream or returns null if the stream has been exhausted
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
     * Returns the next token in the stream that is not skipable or null if the stream has been exhausted or there is
     * no nonskipable token in the stream. The function also advances the stream until after the found token.
     */
    fun safePopNS(): LexToken? {
        while (safePeek()?.isSkipable == true) pop()
        return safePop()
    }

    /**
     * Returns the next token in the stream and advances the stream. If the stream has been exhausted ([isExhausted])
     * then the function throws a [TokenStreamAccessedAfterExhaustedException].
     */
    fun pop(): LexToken {
        val token = peek()
        advanceStream()
        return token
    }

    /**
     * Returns the next token in the stream that is not skipable. The function also advances the stream until after the
     * found token. If the stream has been exhausted or there is no nonskipable token in the stream then the function
     * throws a [TokenStreamAccessedAfterExhaustedException].
     */
    fun popNS(): LexToken {
        while (safePeek()?.isSkipable == true) pop()
        return pop()
    }

    /**
     * Returns true if not [isExhausted] and the type of [peek] == [type].
     */
    fun peekIsType(type: LexTokenType): Boolean = safePeek()?.type == type

    /**
     * Returns true if not [isExhausted] and the type of [peekNS] == [type].
     */
    fun peekNSIsType(type: LexTokenType): Boolean = safePeekNS()?.type == type

    /**
     * Returns the next token while advancing the stream ([pop]) if [peekIsType] of [type] is true otherwise null.
     */
    fun eat(type: LexTokenType): LexToken? = if (peekIsType(type)) pop() else null

    /**
     * Returns the next nonskipable token while advancing the stream ([popNS]) if [peekNSIsType] of [type] is true
     * otherwise returns null.
     */
    fun eatNS(type: LexTokenType): LexToken? = if (peekNSIsType(type)) popNS() else null
}


/**
 * A [TokenStreamAccessedAfterExhaustedException] is thrown when a token of a [LexTokenStream] is accessed
 * after the stream has been exhausted ([LexTokenStream.isExhausted] is true).
 */
class TokenStreamAccessedAfterExhaustedException : Exception()


