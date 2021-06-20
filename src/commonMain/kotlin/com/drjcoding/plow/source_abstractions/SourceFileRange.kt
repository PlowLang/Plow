package com.drjcoding.plow.source_abstractions

/**
 * Represents a range of characters within a source file.
 *
 * @property start The first character of the range (inclusive).
 * @property length The number of characters in the range.
 */
data class SourceFileRange(val start: SourceFileLocation, val length: Int)
