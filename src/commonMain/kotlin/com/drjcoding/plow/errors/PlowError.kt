package com.drjcoding.plow.errors

/**
 * A [PlowError] indicates a fatal [PlowIssue].
 */
open class PlowError(
    errorName: String,
    mainInfo: PlowIssueInfo,
    notes: Collection<PlowIssueInfo> = listOf()
) : PlowIssue(errorName, mainInfo, notes)
