package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.source_abstractions.SourceString

abstract class ObjectDeclarationASTNode(
    final override val name: SourceString,
    final override val parentNamespace: NamespaceASTNode,
    val memberFunctions: List<FunctionDeclarationASTNode>,
    val declarations: List<DeclarationASTNode>,
): ASTNode(), DeclarationASTNode {
    override val childNamespaces = declarations
}