package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.IRTopLevelObject
import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

data class PlowFileASTNode(
    override val name: SourceString,
    override val parentNamespace: FolderASTNode,
    val imports: List<ImportASTNode>,
    val declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), NamespaceASTNode {
    override val childNamespaces = declarations

    /**
     * Converts this file to a series of [IRTopLevelObject]. This is a flat list of all [IRTopLevelObject] produced by
     * any declarations in this file.
     */
    fun toIR(thisFilesLocation: FullyQualifiedLocation): List<IRTopLevelObject> {
        TODO()
    }

    override fun thisNamespacesType(): IRType? = null
}