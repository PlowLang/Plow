package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class PlowFileASTNode(
    val name: SourceString,
    override val parentNamespace: FolderASTNode,
    val imports: List<ImportASTNode>,
    val declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), NamespaceASTNode {
    override val thisNamespace = parentNamespace.thisNamespace.child(name)

    override val childNamespaces = declarations

    override val importedNamespaces: List<QualifiedIdentifierASTNode> = imports.map { it.declarationToImport }

    override fun thisNamespacesType(): IRType? = null
}