package com.drjcoding.plow.source_abstractions

import com.drjcoding.plow.issues.PlowIssueTextRange

/**
 * Represents a range of characters within a source file.
 *
 * @property start The first character of the range (inclusive).
 * @property length The number of characters in the range.
 */
data class SourceFileRange(val start: SourceFileLocation, val length: Int) {

    /**
     * Returns the two [SourceFileRange]s concatenated together by adding their lengths. This only works for two ranges
     * that are adjacent with no gaps in between.
     */
    operator fun plus(other: SourceFileRange) = SourceFileRange(this.start, this.length + other.length)

    /**
     * Returns a new [SourceFileRange] with the same start location but a length [additionalLength] longer.
     */
    fun plusLength(additionalLength: Int) = SourceFileRange(this.start, this.length + additionalLength)
}

/**
 * Converts this [SourceFileRange] to a [PlowIssueTextRange.SameFile].
 */
fun SourceFileRange.toPlowIssueTextRange() = PlowIssueTextRange.SameFile(this)
