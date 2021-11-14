package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.plow_project.TypeResolutionHierarchy

class RootFolderASTNode: FolderASTNode() {
    override lateinit var childNamespaces: List<NamespaceASTNode>

    override val thisNamespace = FullyQualifiedLocation(listOf())

    override val parentNamespace = this

    override val importedNamespaces: List<QualifiedIdentifierASTNode> = listOf()

    override val thisNamespacesType: ObjectType? = null

    override val typeResolutionHierarchy = TypeResolutionHierarchy()
}