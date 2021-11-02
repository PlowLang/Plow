package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.global_values.Global
import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

abstract class ObjectDeclarationASTNode(
    final override val name: SourceString,
    final override val parentNamespace: NamespaceASTNode,
    val memberFunctions: List<FunctionDeclarationASTNode>,
    val declarations: List<DeclarationASTNode>,
): ASTNode(), DeclarationASTNode {
    override val childNamespaces = declarations

    override fun collectGlobalTypes(parentNamespace: FullyQualifiedLocation): List<IRType> {
        val childNamespace = parentNamespace.child(name)
        return declarations.map { it.collectThisNamespacesGlobalTypes(childNamespace) }.flatten() + ObjectType(parentNamespace, name)
    }

    override fun collectGlobals(parentNamespace: FullyQualifiedLocation): List<Global> {
        val childNamespace = parentNamespace.child(name)
        return declarations.map { it.collectGlobals(childNamespace) }.flatten()
    }
}