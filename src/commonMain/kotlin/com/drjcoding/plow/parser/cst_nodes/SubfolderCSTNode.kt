package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.SubfolderASTNode
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Represents a folder in the CST hierarchy.
 */
data class SubfolderCSTNode(
    val name: SourceString,
    val parent: FolderCSTNode,
): FolderCSTNode() {
    override fun toNamespaceASTNode(): SubfolderASTNode =
        SubfolderASTNode(name, parent.toNamespaceASTNode(), children.map { it.toNamespaceASTNode() })
}