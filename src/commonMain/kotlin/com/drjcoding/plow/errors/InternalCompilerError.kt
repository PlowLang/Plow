package com.drjcoding.plow.errors

/**
 * An [InternalCompilerError] is the only type of [PlowIssue] not caused by the user. It occurs when the compiler throws
 * an exception that is not caught and handled. An [InternalCompilerError] is always a bug in the compiler.
 * [InternalCompilerError] exists so that bugs in the compiler can be communicated to the user using the same system as
 * normal code problems.
 */
class InternalCompilerError(causingException: Exception) : PlowError(
    "internal compiler error:\n\n$causingException",
    null,
    listOf(
        PlowIssueNote(null, "The compiler unexpectedly threw an exception. This is a bug."),
        PlowIssueNote(null, "Please report this bug at https://github.com/PlowLang/Plow/issues.")
    )
)
