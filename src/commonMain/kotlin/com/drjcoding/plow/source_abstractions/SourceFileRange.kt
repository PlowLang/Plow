package com.drjcoding.plow.source_abstractions

import com.drjcoding.plow.errors.PlowIssueTextRange

/**
 * Represents a range of characters within a source file.
 *
 * @property start The first character of the range (inclusive).
 * @property length The number of characters in the range.
 */
data class SourceFileRange(val start: SourceFileLocation, val length: Int)

/**
 * Converts this [SourceFileRange] to a [PlowIssueTextRange.SameFile].
 */
fun SourceFileRange.toPlowIssueTextRange() = PlowIssueTextRange.SameFile(this)
