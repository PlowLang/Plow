package com.drjcoding.plow.lexer

import com.drjcoding.plow.source_abstractions.SourceFileLocation
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toSourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

/**
 * Represents a lexical token of text in a source file.
 *
 * @property type The microsyntactic class of the token.
 * @property text The literal text that makes up the token.
 * @property location The location in the source file that the token was found.
 */
data class LexToken(val type: LexTokenType, val text: SourceString, val location: SourceFileLocation) {

    constructor(type: LexTokenType, text: String, location: SourceFileLocation) :
            this(type, text.toSourceString(), location)

    /**
     * True if this token can be safely ignored in between other tokens (ex. whitespace and comments).
     */
    val isSkipable: Boolean
        get() = type.isSkipable

    /**
     * The length of [text].
     */
    val textLength: Int
        get() = text.toUnderlyingString().length
}
