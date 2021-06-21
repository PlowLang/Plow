package com.drjcoding.plow.errors

import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * Represents a range of text relevant to a [PlowIssue].
 */
sealed class PlowIssueTextRange {

    /**
     * A piece of text in the same file as where the [PlowIssue] occurred.
     */
    data class SameFile(val sourceFileRange: SourceFileRange): PlowIssueTextRange()
}
