package com.drjcoding.plow.issues

/**
 * A [PlowIssue] indicates any problem that was encountered while attempting to compile plow code.
 *
 * @property errorName Tells the user what the error is. It does not provide information about the specific instance of
 * the error. It is generic to the error message type.
 * @property mainInfo Provides the main body of information to the user.
 * @property notes Provides additional information to the user.
 */
interface PlowIssue {
    val errorName: String
    val mainInfo: PlowIssueInfo
    val notes: Collection<PlowIssueInfo>
}