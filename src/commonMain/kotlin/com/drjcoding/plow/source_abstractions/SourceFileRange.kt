package com.drjcoding.plow.source_abstractions

import com.drjcoding.plow.issues.PlowIssueTextRange

/**
 * Represents a range of characters within a source file.
 *
 * @property start The first character of the range (inclusive).
 * @property end The final character of the range (exclusive).
 */
data class SourceFileRange(val start: SourceFileLocation, val end: SourceFileLocation) {

    /**
     * Returns the two [SourceFileRange]s concatenated together (`this.start` to `other.end`)
     */
    operator fun plus(other: SourceFileRange) = SourceFileRange(this.start, other.end)

}

/**
 * Converts this [SourceFileRange] to a [PlowIssueTextRange.SameFile].
 */
fun SourceFileRange.toPlowIssueTextRange() = PlowIssueTextRange.SameFile(this)
