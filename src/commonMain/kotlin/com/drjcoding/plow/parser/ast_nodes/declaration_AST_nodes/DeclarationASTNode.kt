package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode

interface DeclarationASTNode : NamespaceASTNode {

    // Right now we cannot create new imports in declarations, so we just use our parent's imports.
    override val importedNamespaces: List<QualifiedIdentifierASTNode>
        get() = parentNamespace.importedNamespaces
}