package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode

/**
 * A namespace in a CST node.
 */
interface NamespaceCSTNode {

    /**
     * Converts this to a [NamespaceASTNode].
     */
    fun toNamespaceASTNode(): NamespaceASTNode
}