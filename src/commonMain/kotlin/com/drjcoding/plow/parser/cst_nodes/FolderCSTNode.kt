package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.FolderASTNode
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Represents a folder in the CST hierarchy.
 */
data class FolderCSTNode(
    val name: SourceString,
    val parent: FolderCSTNode?,
    val children: List<NamespaceCSTNode>
): NamespaceCSTNode {
    override fun toNamespaceASTNode(): FolderASTNode =
        FolderASTNode(name, parent?.toNamespaceASTNode(), children.map { it.toNamespaceASTNode() })
}