package com.drjcoding.plow.errors

/**
 * A [PlowWarning] indicates a non fatal [PlowIssue].
 */
open class PlowWarning(
    errorName: String,
    mainInfo: PlowIssueInfo,
    notes: Collection<PlowIssueInfo> = listOf()
) : PlowIssue(errorName, mainInfo, notes)
