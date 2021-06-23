package com.drjcoding.plow.errors

/**
 * An [PlowIssueInfo] provides additional context or information to a [PlowIssue].
 *
 * @property textRange The piece of text the [PlowIssueInfo] provides context to.
 * @property message The information the note provides.
 */
open class PlowIssueInfo(val textRange: PlowIssueTextRange?, val message: String)
