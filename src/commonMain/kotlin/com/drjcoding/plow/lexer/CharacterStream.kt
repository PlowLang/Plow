package com.drjcoding.plow.lexer

import com.drjcoding.plow.source_abstractions.SourceFileLocation
import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * A stream of characters that can be used for lexing.
 */
class CharacterStream(private val text: String) {
    private var currentCharPosition = 0
    private var currentLineNumber = 1
    private var currentColumnNumber = 1

    /**
     * Advances the stream one character [times] times by incrementing [currentCharPosition] and updates
     * [currentLineNumber] and [currentColumnNumber].
     */
    private fun advanceStream(times: Int = 1) {
        repeat(times) {
            if (safePeek() == '\n') {
                currentLineNumber++
                currentColumnNumber = 1
            } else {
                currentColumnNumber++
            }
            currentCharPosition++
        }
    }

    /**
     * Whether the stream has been exhausted.
     */
    val isEOF: Boolean
        get() = currentCharPosition >= text.length

    /**
     * A [SourceFileLocation] representing the character that would be accessed by [peek].
     */
    val sourceFileLocation: SourceFileLocation
        get() = SourceFileLocation(currentLineNumber, currentColumnNumber)

    /**
     * Returns a range from [from] to [sourceFileLocation].
     */
    fun rangeToCurrent(from: SourceFileLocation) = SourceFileRange(from, sourceFileLocation)

    /**
     * Returns the next character in the stream without advancing the stream or null if the stream has been exhausted
     * ([isEOF]).
     */
    fun safePeek(): Char? = if (isEOF) null else text[currentCharPosition]

    /**
     * Returns the next [n] characters of the stream without advancing the stream or null if that many characters
     * would extend beyond the length of the stream.
     */
    fun safePeek(n: Int): String? =
        if (currentCharPosition + n - 1 >= text.length) null else
            text.substring(currentCharPosition, currentCharPosition + n)

    /**
     * Returns the next character in the stream without advancing the stream. If the stream has been exhausted ([isEOF])
     * then the function throws a [CharacterStreamAccessedAfterExhaustedException].
     */
    fun peek(): Char = if (isEOF) throw CharacterStreamAccessedAfterExhaustedException() else text[currentCharPosition]

    /**
     * Returns the next [n] characters of the stream without advancing the stream. If [n] characters would extend
     * beyond the end of the stream the function throws a [CharacterStreamAccessedAfterExhaustedException].
     */
    fun peek(n: Int): String = safePeek(n) ?: throw CharacterStreamAccessedAfterExhaustedException()

    /**
     * Returns true if [text] is the next text in the stream.
     */
    fun textIsNext(text: String): Boolean = safePeek(text.length) == text

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

    /**
     * Returns the next [n] characters in the stream and advances the stream [n] characters. If the stream is exhausted
     * by this many characters then the function throws a [CharacterStreamAccessedAfterExhaustedException].
     */
    fun pop(n: Int): String {
        val str = peek(n)
        advanceStream(n)
        return str
    }
}


/**
 * A [CharacterStreamAccessedAfterExhaustedException] is thrown when a character of a [CharacterStream] is accessed
 * after the stream has been exhausted ([CharacterStream.isEOF] is true).
 */
class CharacterStreamAccessedAfterExhaustedException : Exception()
