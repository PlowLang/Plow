package com.drjcoding.plow.lexer

/**
 * A stream of characters that can be used for lexing.
 */
class CharacterStream(private val text: String) {
    private var currentCharPosition = 0
    private var currentLineNumber = 1
    private var currentColumnNumber = 1

    /**
     * Advances the stream one character by incrementing [currentCharPosition] and updates [currentLineNumber] and
     * [currentColumnNumber].
     */
    private fun advanceStream() {
        currentCharPosition++
        if (safePeek() == '\n') {
            currentLineNumber++
            currentColumnNumber = 1
        }
    }

    /**
     * Whether or not the stream has been exhausted.
     */
    val isEOF: Boolean
        get() = currentCharPosition >= text.length

    /**
     * A [SourceFileLocation] representing the character that would be accessed by [peek].
     */
    val sourceFileLocation: SourceFileLocation
        get() = SourceFileLocation(currentLineNumber, currentColumnNumber)

    /**
     * Returns the next character in the stream without advancing the stream or null if the stream has been exhausted
     * ([isEOF]).
     */
    fun safePeek(): Char? = if (isEOF) null else text[currentCharPosition]

    /**
     * Returns the next character in the stream without advancing the stream. If the stream has been exhausted ([isEOF])
     * then the function throws a [CharacterStreamAccessedAfterExhaustedException].
     */
    fun peek(): Char = if (isEOF) throw CharacterStreamAccessedAfterExhaustedException() else text[currentCharPosition]

    /**
     * Returns the next character in the stream and advance the stream or returns null if the stream has been exhausted
     * ([isEOF]).
     */
    fun safePop(): Char? {
        val char = safePeek()
        if (char != null) {
            advanceStream()
        }
        return char
    }

    /**
     * Returns the next character in the stream and advances the stream. If the stream has been exhausted ([isEOF]) then
     * the function throws a [CharacterStreamAccessedAfterExhaustedException].
     */
    fun pop(): Char {
        val char = peek()
        advanceStream()
        return char
    }
}


/**
 * A [CharacterStreamAccessedAfterExhaustedException] is thrown when a character of a [CharacterStream] is accessed
 * after the stream has been exhausted ([CharacterStream.isEOF] is true).
 */
class CharacterStreamAccessedAfterExhaustedException : Exception()

