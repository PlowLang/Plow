package com.drjcoding.plow.lexer

import com.drjcoding.plow.source_abstractions.SourceFileLocation

/**
 * Represents a lexical token of text in a source file.
 *
 * @property type The microsyntactic class of the token.
 * @property text The literal text that makes up the token.
 * @property location The location in the source file that the token was found.
 */
data class LexToken(val type: LexTokenType, val text: String, val location: SourceFileLocation)
