package com.drjcoding.plow.errors

/**
 * An [PlowIssueNote] provides additional context or information to a [PlowIssue].
 *
 * @property textRange The piece of text the [PlowIssueNote] provides context to.
 * @property message The information the note provides.
 */
open class PlowIssueNote(val textRange: PlowIssueTextRange?, val message: String)
