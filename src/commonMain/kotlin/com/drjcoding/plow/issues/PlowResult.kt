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

        fun <U> changeType() = Error<U>(issues)
    }

    /**
     * If this is [Ok] then this is returned otherwise a [UnexpectedlyFoundNullWhileUnwrappingException] is thrown.
     */
    fun unwrap(): T =
        when (this) {
            is Ok -> this.result
            is Error -> throw UnexpectedlyFoundNullWhileUnwrappingException(this.issues)
        }

    /**
     * If this is [Ok] then the result is returned otherwise the singular [PlowFatalIssue] inside the [Error] is thrown.
     * If the [Error] does not contain a singular [PlowFatalIssue] and [UnexpectedlyFoundNullWhileUnwrappingException]
     * is thrown.
     */
    fun unwrapThrowingErrors(): T =
        when (this) {
            is Ok -> this.result
            is Error -> {
                if (!issues.all { it is PlowFatalIssue }) throw UnexpectedlyFoundNullWhileUnwrappingException(this.issues)
                throw when (issues.size) {
                    0 -> UnexpectedlyFoundNullWhileUnwrappingException(this.issues)
                    1 -> this.issues.first() as PlowFatalIssue
                    else -> CombinedPlowIssues(issues.map { it as PlowFatalIssue })
                }
            }
        }

    /**
     * If this is [Ok] returns [Ok.result] run through mapper, otherwise returns the [PlowResult.Error]
     */
    fun <U> map(mapper: (T) -> U): PlowResult<U> =
        when (this) {
            is Ok -> Ok(mapper(this.result), this.issues)
            is Error -> Error(this.issues)
        }

}

class CombinedPlowIssues(
    val issues: List<PlowFatalIssue>
) : Exception()

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
    } catch (combined: CombinedPlowIssues) {
        PlowResult.Error(combined.issues)
    } catch (e: Exception) {
        val ice = InternalCompilerError(e)
        PlowResult.Error(listOf(ice))
    }


/**
 * Converts this to a [PlowResult] by wrapping it in [PlowResult.Ok]
 */
fun <T> T.toPlowResult() = PlowResult.Ok(this)

/**
 * If the list contains any [PlowResult.Error]s returns a [PlowResult.Error] containing all the issues, otherwise
 * returns a [PlowResult.Ok] containing the list of items.
 */
fun <T> List<PlowResult<T>>.flattenToPlowResult(): PlowResult<List<T>> {
    val issues = this.map {
        when(it) {
            is PlowResult.Ok -> listOf()
            is PlowResult.Error -> it.issues
        }
    }.flatten()
    return if (issues.isEmpty()) this.map { it.unwrap() }.toPlowResult() else PlowResult.Error(issues)
}
