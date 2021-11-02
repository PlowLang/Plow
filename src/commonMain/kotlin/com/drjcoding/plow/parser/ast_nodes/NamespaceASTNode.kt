package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * An [ASTNode] that is a namespace.
 */
interface NamespaceASTNode {

    /**
     * The name of this namespace.
     */
    val name: SourceString

    /**
     * The namespace that contains this namespace.
     */
    val parentNamespace: NamespaceASTNode?

    /**
     * All the child namespaces of this namespace.
     */
    val childNamespaces: List<NamespaceASTNode>

    /**
     * The full location of this namespace. This includes this namespace in the location.
     */
    val thisNamespace: FullyQualifiedLocation
        get() = parentNamespace?.thisNamespace?.child(name) ?: FullyQualifiedLocation(listOf(name))

    /**
     * Gets the type that this namespace represents (if there is one).
     */
    fun thisNamespacesType(): IRType?
}