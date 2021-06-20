package com.drjcoding.plow.errors

/**
 * A [PlowError] indicates a fatal [PlowIssue].
 */
open class PlowError(
    message: String,
    textRange: PlowIssueTextRange?,
    notes: Collection<PlowIssueNote>
) : PlowIssue(message, textRange, notes)
