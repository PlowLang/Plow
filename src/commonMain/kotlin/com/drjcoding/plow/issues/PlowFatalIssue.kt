package com.drjcoding.plow.issues

/**
 * A [PlowIssue] that is fatal. (i.e. it must cause compilation to halt).
 */
open class PlowFatalIssue(
    override val errorName: String,
    override val mainInfo: PlowIssueInfo,
    override val notes: Collection<PlowIssueInfo> = listOf()
) : Exception(errorName), PlowIssue