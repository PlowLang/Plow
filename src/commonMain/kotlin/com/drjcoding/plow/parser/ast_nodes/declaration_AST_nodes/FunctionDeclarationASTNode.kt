package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class FunctionDeclarationASTNode(
    override val name: SourceString,
    override val parentNamespace: NamespaceASTNode,
    val args: List<FunctionDeclarationArgASTNode>,
    val returnType: TypeASTNode?,
    val body: CodeBlockASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), DeclarationASTNode {
    override val childNamespaces = listOf<NamespaceASTNode>()

    override fun thisNamespacesType(): IRType? = null
}

data class FunctionDeclarationArgASTNode(
    val name: SourceString,
    val type: TypeASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode()