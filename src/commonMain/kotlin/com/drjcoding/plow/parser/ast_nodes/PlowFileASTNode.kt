package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.plow_project.TypeResolutionHierarchy
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

    override val typeResolutionHierarchy = TypeResolutionHierarchy().also { trh ->
        imports.forEach { trh.addNamespaces(it.declarationToImport.fullyQualifiedLocationWithName) }
    }

    override fun addParentToTRH(trh: TypeResolutionHierarchy) {
        trh.decreasePriority()
        trh.addNamespaces(thisNamespace)
        // We don't want to move up the chain anymore, so we overrode this function.
    }

    override val thisNamespacesType: ObjectType? = null
}