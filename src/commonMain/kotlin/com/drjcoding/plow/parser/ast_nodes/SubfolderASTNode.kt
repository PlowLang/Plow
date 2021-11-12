package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Represents a folder in a plow project.
 */
data class SubfolderASTNode(
    override val name: SourceString,
    override val parentNamespace: FolderASTNode,
    override val childNamespaces: List<NamespaceASTNode>
): NamespaceASTNode, FolderASTNode() {
    override fun thisNamespacesType(): IRType? = null
}