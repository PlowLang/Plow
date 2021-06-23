package com.drjcoding.plow.issues

/**
 * A [PlowWarning] indicates any nonfatal problem with user inputted data: code, configuration, command line args.
 *
 * @see PlowError
 */
open class PlowWarning(
    errorName: String,
    mainInfo: PlowIssueInfo,
    notes: Collection<PlowIssueInfo> = listOf()
) : PlowNonfatalIssue(errorName, mainInfo, notes)
