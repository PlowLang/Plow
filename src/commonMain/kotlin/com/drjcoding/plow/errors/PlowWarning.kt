package com.drjcoding.plow.errors

/**
 * A [PlowWarning] indicates a non fatal [PlowIssue].
 */
open class PlowWarning(
    message: String,
    textRange: PlowIssueTextRange?,
    notes: Collection<PlowIssueNote>
) : PlowIssue(message, textRange, notes)
