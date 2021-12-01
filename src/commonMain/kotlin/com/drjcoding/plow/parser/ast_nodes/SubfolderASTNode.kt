package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.plow_project.TypeResolutionHierarchy
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Represents a folder in a plow project.
 */
data class SubfolderASTNode(
    val name: SourceString,
    override val childNamespaces: List<NamespaceASTNode>
): NamespaceASTNode, FolderASTNode() {
    override lateinit var parentNamespace: NamespaceASTNode

    override val thisNamespace: FullyQualifiedLocation
        get() = parentNamespace.thisNamespace.child(name)

    override val importedNamespaces: List<QualifiedIdentifierASTNode> = listOf()

    override val thisNamespacesType: ObjectType? = null

    override val typeResolutionHierarchy = TypeResolutionHierarchy()

}