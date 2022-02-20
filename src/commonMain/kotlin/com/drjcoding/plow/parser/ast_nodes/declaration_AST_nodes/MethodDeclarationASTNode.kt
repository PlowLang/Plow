package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

class MethodDeclarationASTNode(
    private val underlyingFunction: BaseFunctionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode()