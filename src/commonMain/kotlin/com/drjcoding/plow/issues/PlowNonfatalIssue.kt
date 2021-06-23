package com.drjcoding.plow.issues

/**
 * Indicates a [PlowIssue] that is nonfatal. (ie. compilation may continue even after the issue occurs).
 */
open class PlowNonfatalIssue(
    override val errorName: String,
    override val mainInfo: PlowIssueInfo,
    override val notes: Collection<PlowIssueInfo> = listOf()
) : Exception(errorName), PlowIssue