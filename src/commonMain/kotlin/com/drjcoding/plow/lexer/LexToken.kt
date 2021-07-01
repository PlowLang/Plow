package com.drjcoding.plow.lexer

import com.drjcoding.plow.source_abstractions.*

/**
 * Represents a lexical token of text in a source file.
 *
 * @property type The microsyntactic class of the token.
 * @property text The literal text that makes up the token.
 * @property range The range in the source file that the token was found.
 */
data class LexToken(val type: LexTokenType, val text: SourceString, val range: SourceFileRange) {

    constructor(type: LexTokenType, text: String, range: SourceFileRange) :
            this(type, text.toSourceString(), range)

    /**
     * True if this token can be safely ignored in between other tokens (ex. whitespace and comments).
     */
    val isSkipable: Boolean
        get() = type.isSkipable

    override fun equals(other: Any?) =
        other != null &&
                other is LexToken &&
                this.type == other.type &&
                this.text == other.text &&
                this.range == other.range

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + text
        result = 31 * result + range.hashCode()
        return result
    }
}
