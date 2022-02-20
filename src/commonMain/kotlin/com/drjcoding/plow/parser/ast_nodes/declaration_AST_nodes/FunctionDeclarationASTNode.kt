package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.DeclarationCSTNode

class FunctionDeclarationASTNode(
    val underlyingFunction: BaseFunctionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), TopLevelDeclarationASTNode, DeclarationCSTNode.FileChildASTNode
