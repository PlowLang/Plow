package com.drjcoding.plow.issues

/**
 * A [PlowError] indicates any fatal problem with user inputted data: code, configuration, command line args.
 *
 * @see PlowWarning
 */
open class PlowError(
    errorName: String,
    mainInfo: PlowIssueInfo,
    notes: Collection<PlowIssueInfo> = listOf()
) : PlowFatalIssue(errorName, mainInfo, notes)
