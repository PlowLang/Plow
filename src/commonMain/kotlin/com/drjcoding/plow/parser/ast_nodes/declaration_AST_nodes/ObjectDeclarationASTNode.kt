package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.plow_project.TypeResolutionHierarchy
import com.drjcoding.plow.source_abstractions.SourceString

abstract class ObjectDeclarationASTNode(
    val name: SourceString,
    final override val parentNamespace: NamespaceASTNode,
    val memberFunctions: List<FunctionDeclarationASTNode>,
    val declarations: List<DeclarationASTNode>,
): ASTNode(), DeclarationASTNode {
    override val childNamespaces = declarations

    final override val thisNamespace: FullyQualifiedLocation
        get() = parentNamespace.thisNamespace.child(name)

    override val thisNamespacesType: ObjectType = ObjectType(thisNamespace, name)

    override val typeResolutionHierarchy = TypeResolutionHierarchy().also { trh ->
        addParentToTRH(trh)
        trh.decreasePriority()
        importedNamespaces.forEach { trh.addNamespaces(it.fullyQualifiedLocationWithName) }
    }
}