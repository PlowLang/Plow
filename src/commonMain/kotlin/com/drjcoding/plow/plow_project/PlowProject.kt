package com.drjcoding.plow.plow_project

/**
 * A [PlowProject] is made up of a series of [PlowProjectSourceFile]s.
 */
class PlowProject(
    val sourceFiles: List<PlowProjectSourceFile>
)

/**
 * A [PlowProjectSourceFile] represents an unprocessed Plow file. (i.e. one containing source code.)
 */
data class PlowProjectSourceFile (
    val location: PlowProjectFileLocation,
    val code: String
)