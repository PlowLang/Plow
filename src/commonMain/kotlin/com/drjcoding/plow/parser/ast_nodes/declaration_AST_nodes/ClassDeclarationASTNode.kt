package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class ClassDeclarationASTNode(
    val name: SourceString,
    val declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode,
) : ASTNode(), DeclarationASTNode