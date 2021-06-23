package com.drjcoding.plow.issues

/**
 * An [InternalCompilerError] occurs when the compiler throws an exception that is not caught and handled. An
 * [InternalCompilerError] is always a bug in the compiler.
 */
class InternalCompilerError(causingException: Exception) : PlowFatalIssue(
    "internal compiler error",
    PlowIssueInfo(null, "The compiler threw the following exception:\n\n$causingException."),
    listOf(
        PlowIssueInfo(null, "The compiler unexpectedly threw an exception. This is a bug."),
        PlowIssueInfo(null, "Please report this bug at https://github.com/PlowLang/Plow/issues.")
    )
)
