package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.plow_project.GlobalTypesManager

/**
 * An [ASTNode] that is a namespace.
 */
interface NamespaceASTNode {

    /**
     * The namespace that contains this namespace.
     */
    val parentNamespace: NamespaceASTNode

    /**
     * The full location of this namespace. This includes this namespace in the location.
     */
    val thisNamespace: FullyQualifiedLocation

    /**
     * All the child namespaces of this namespace.
     */
    val childNamespaces: List<NamespaceASTNode>

    /**
     * All the namespaces that have been imported into this namespace or its parents.
     */
    val importedNamespaces: List<QualifiedIdentifierASTNode>

    /**
     * Gets the type that this namespace represents (if there is one).
     */
    fun thisNamespacesType(): ObjectType?

    /**
     * Apply [apply] to this namespace and all its children.
     */
    fun <T> forEach(apply: (NamespaceASTNode) -> T) {
        apply(this)
        childNamespaces.forEach { it.forEach(apply) }
    }

    /**
     * Gets the type associated with a certain name if one exists.
     */
    fun getIRTypeForName(name: QualifiedIdentifierASTNode, manager: GlobalTypesManager): IRType? {
        TODO()
    }
}