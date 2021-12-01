package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.RootFolderASTNode

/**
 * TODO
 */
class RootFolderCSTNode: FolderCSTNode() {
    override fun toNamespaceASTNode(): RootFolderASTNode {
        val root = RootFolderASTNode()
        val childrenAST = children.map { it.toNamespaceASTNode() }
        root.childNamespaces = childrenAST
        childrenAST.forEach { it.parentNamespace = root }
        return root
    }
}