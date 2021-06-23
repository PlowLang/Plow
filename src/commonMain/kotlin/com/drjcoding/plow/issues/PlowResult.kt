package com.drjcoding.plow.issues

/**
 * The result of any Plow operation that could throw an error. [T] is the type of the data the operation is expected to
 * produce.
 */
sealed class PlowResult<T> {
    /**
     * The operation succeeded.
     *
     * @property result The data produced by the operation.
     * @property issues Any [PlowNonfatalIssue]s produced by the operation.
     */
    class Ok<T>(val result: T, val issues: Collection<PlowNonfatalIssue> = listOf()) : PlowResult<T>()

    /**
     * The operation failed.
     *
     * @property issues The [PlowIssue]s produced by the operations. (Non-empty).
     */
    class Error<T>(val issues: Collection<PlowIssue>) : PlowResult<T>()
}