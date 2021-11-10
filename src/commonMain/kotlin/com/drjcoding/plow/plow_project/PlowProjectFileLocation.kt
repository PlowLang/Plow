package com.drjcoding.plow.plow_project

import com.drjcoding.plow.parser.cst_nodes.FolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.RootFolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.SubfolderCSTNode
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [PlowProjectFileLocation] fully describes the position of any source file, or object derived from a source file, in
 * a Plow project.
 */
data class PlowProjectFileLocation(
    val folderNames: List<SourceString>,
    val fileName: SourceString
) {

    fun inFolder(topFolder: FolderCSTNode): FolderCSTNode {
        var folder = topFolder
        folderNames.forEach {
            folder = topFolder.childFolderWithName(it)!!
        }
        return folder
    }
}

fun locationsToFolderTree(locations: List<PlowProjectFileLocation>): RootFolderCSTNode {
    val root = RootFolderCSTNode()
    TODO()
}