package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation

class RootFolderASTNode: FolderASTNode() {
    override lateinit var childNamespaces: List<NamespaceASTNode>

    override val thisNamespace = FullyQualifiedLocation(listOf())

    override val parentNamespace = this

    override val importedNamespaces: List<QualifiedIdentifierASTNode> = listOf()

    override fun thisNamespacesType(): IRType? = null
}