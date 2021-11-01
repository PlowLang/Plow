package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ImportASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class PlowFileASTNode(
    val imports: List<ImportASTNode>,
    val declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode()