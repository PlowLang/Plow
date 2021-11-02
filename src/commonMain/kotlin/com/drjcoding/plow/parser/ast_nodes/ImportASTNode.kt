package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class ImportASTNode(
    val declarationToImport: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode()