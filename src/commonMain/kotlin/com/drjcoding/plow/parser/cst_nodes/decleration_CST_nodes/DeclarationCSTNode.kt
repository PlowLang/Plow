package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.StatementCSTNode

interface DeclarationCSTNode: StatementCSTNode {
    override fun toAST(): DeclarationASTNode
}