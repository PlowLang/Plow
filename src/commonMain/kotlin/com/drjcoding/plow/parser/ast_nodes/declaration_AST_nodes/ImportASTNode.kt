package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class ImportASTNode(
    val declarationToImport: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), DeclarationASTNode