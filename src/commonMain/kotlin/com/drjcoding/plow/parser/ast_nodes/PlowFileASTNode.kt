package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ImportASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.DeclarationCSTNode

data class PlowFileASTNode(
    val imports: List<ImportASTNode>,
    val toplevelDeclarations: List<DeclarationCSTNode.FileChildASTNode>,
    override val underlyingCSTNode: CSTNode,
) : ASTNode()