package com.drjcoding.plow.issues

/**
 * The result of any Plow operation that could throw an error. [T] is the type of the data the operation is expected to
 * produce.
 */
sealed class PlowResult<out T> {
    /**
     * The operation succeeded.
     *
     * @property result The data produced by the operation.
     * @property issues Any [PlowNonfatalIssue]s produced by the operation.
     */
    class Ok<out T>(val result: T, val issues: Collection<PlowNonfatalIssue> = listOf()) : PlowResult<T>()

    /**
     * The operation failed.
     *
     * @property issues The [PlowIssue]s produced by the operations. (Non-empty).
     */
    class Error<out T>(val issues: Collection<PlowIssue>) : PlowResult<T>() {
        constructor(issue: PlowIssue) : this(listOf(issue))
    }

    /**
     * If this is [Ok] then this is returned otherwise a [UnexpectedlyFoundNullWhileUnwrappingException] is thrown.
     */
    fun unwrap(): T =
        when (this) {
            is Ok<T> -> this.result
            is Error<T> -> throw UnexpectedlyFoundNullWhileUnwrappingException(this.issues)
        }

    /**
     * If this is [Error] returns this, otherwise returns [Ok] wrapping [Ok.result] applied to [mapper].
     */
    fun <U> map(mapper: (T) -> U): PlowResult<U> =
        when (this) {
            is Ok -> Ok(mapper(this.result))
            is Error -> Error(this.issues)
        }

}

/**
 * If all the [PlowResult]s in this list are [PlowResult.Ok] then this returns a [PlowResult.Ok] containing a list of
 * all the results, otherwise it returns a [PlowResult.Error] containing all the errors from all the [PlowResult.Error]s
 * in the list.
 */
fun <T> List<PlowResult<T>>.flatten(): PlowResult<List<T>> {
    val issues: MutableList<PlowIssue> = mutableListOf()
    val results: MutableList<T> = mutableListOf()
    for (res in this) {
        when (res) {
            is PlowResult.Ok -> results.add(res.result)
            is PlowResult.Error -> issues.addAll(res.issues)
        }
    }
    return when {
        issues.isEmpty() -> PlowResult.Ok(results)
        else -> PlowResult.Error(issues)
    }
}

/**
 * Thrown when [PlowResult.unwrap] is called on a [PlowResult.Error].
 *
 * @property issues The issues of the found [PlowResult.Error]
 */
class UnexpectedlyFoundNullWhileUnwrappingException(val issues: Collection<PlowIssue>) : Exception()

/**
 * Runs the given block and catches any exceptions that occur. Returns a [PlowResult] based on the exceptions that
 * occur.
 * - If a [PlowFatalIssue] is thrown then a [PlowResult.Error] is returned containing the fatal issue.
 * - If any other exception is thrown then a [PlowResult.Error] is returned containing an [InternalCompilerError].
 * - If no exceptions are thrown then a [PlowResult.Ok] is returned containing the result of [run].
 */
inline fun <T> runCatchingExceptionsAsPlowResult(run: () -> T): PlowResult<T> =
    try {
        val result = run()
        result.toPlowResult()
    } catch (pfi: PlowFatalIssue) {
        PlowResult.Error(listOf(pfi))
    } catch (e: Exception) {
        val ice = InternalCompilerError(e)
        PlowResult.Error(listOf(ice))
    }

/**
 * Converts this to a [PlowResult] by wrapping it in [PlowResult.Ok]
 */
fun <T> T.toPlowResult() = PlowResult.Ok(this)