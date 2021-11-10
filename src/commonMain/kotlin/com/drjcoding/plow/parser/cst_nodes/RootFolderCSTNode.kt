package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.FolderASTNode

/**
 * TODO
 */
class RootFolderCSTNode: FolderCSTNode() {
    override lateinit var children: List<SubfolderCSTNode>

    override fun toNamespaceASTNode(): FolderASTNode {
        TODO("Not yet implemented")
    }
}