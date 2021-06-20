package com.drjcoding.plow.errors

/**
 * A [PlowIssue] indicates any problem with user inputted data: code, configuration, command line args. It is not
 * internal exceptions within the compiler. [InternalCompilerError] is the only exception to this rule.
 */
open class PlowIssue(
    override val message: String,
    val textRange: PlowIssueTextRange?,
    val notes: Collection<PlowIssueNote>
) : Exception()
