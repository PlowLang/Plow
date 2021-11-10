package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.FolderASTNode
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * TODO doc comment
 */
abstract class FolderCSTNode: NamespaceCSTNode {
    abstract val children: List<NamespaceCSTNode>

    abstract override fun toNamespaceASTNode(): FolderASTNode

    /**
     * Returns the first direct child folder with the given name or null if no child folder has that name.
     */
    fun childFolderWithName(name: SourceString) =
        children
            .filterIsInstance<SubfolderCSTNode>() //TODO this is bad style
            .firstOrNull { it.name == name}
}