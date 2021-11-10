package com.drjcoding.plow.plow_project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flatten
import com.drjcoding.plow.lexer.lex
import com.drjcoding.plow.parser.cst_nodes.FolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.SubfolderCSTNode

/**
 * A [PlowProject] is made up of a series of [PlowProjectSourceFile]s.
 */
class PlowProject(
    val sourceFiles: List<PlowProjectSourceFile>
) {
    val folder = locationsToFolderTree(sourceFiles.map { it.location })

    //TODO unit test
    fun toLexedPlowProject(): PlowResult<LexedPlowProject> =
        sourceFiles
            .map { it.toLexedPlowProjectFile(it.location.inFolder(folder)!!) }
            .flatten()
            .map(::LexedPlowProject)
}

/**
 * A [PlowProjectSourceFile] represents an unprocessed Plow file. (i.e. one containing source code.)
 */
data class PlowProjectSourceFile(
    val location: PlowProjectFileLocation,
    val code: String
) {
    fun toLexedPlowProjectFile(folder: FolderCSTNode): PlowResult<LexedPlowProjectFile> =
        lex(code).map { LexedPlowProjectFile(location.fileName, folder, it) }

}