package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ExpressionASTNode
import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

data class VariableDeclarationASTNode(
    val name: SourceString,
    override val parentNamespace: NamespaceASTNode,
    val type: TypeASTNode?,
    val value: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), DeclarationASTNode {
    override val thisNamespace = parentNamespace.thisNamespace.child(name)

    override val childNamespaces = listOf<NamespaceASTNode>()

    override val thisNamespacesType: ObjectType? = null

    override val typeResolutionHierarchy = parentNamespace.typeResolutionHierarchy

}