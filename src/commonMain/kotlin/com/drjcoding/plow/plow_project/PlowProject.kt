package com.drjcoding.plow.plow_project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.lex
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [PlowProject] is made up of a series of [PlowProjectSourceFile]s.
 */
class PlowProject(
    val sourceFiles: SimpleFileStructure<PlowProjectSourceFile>
) {
    //TODO unit test
    fun toLexedPlowProject(): PlowResult<LexedPlowProject> =
        sourceFiles
            .mapContent { it.toLexedPlowProjectFile() }
            .flatten()
            .map { LexedPlowProject(it) }
}

/**
 * A [PlowProjectSourceFile] represents an unprocessed Plow file. (i.e. one containing source code.)
 */
data class PlowProjectSourceFile(
    val fileName: SourceString,
    val code: String
) {
    fun toLexedPlowProjectFile(): PlowResult<LexedPlowProjectFile> =
        lex(code).map { LexedPlowProjectFile(fileName, it) }

}