package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.RootFolderASTNode

/**
 * TODO
 */
class RootFolderCSTNode: FolderCSTNode() {
    override fun toNamespaceASTNode(): RootFolderASTNode {
        val root = RootFolderASTNode()
        root.childNamespaces = children.map { it.toNamespaceASTNode() }
        return root
    }
}