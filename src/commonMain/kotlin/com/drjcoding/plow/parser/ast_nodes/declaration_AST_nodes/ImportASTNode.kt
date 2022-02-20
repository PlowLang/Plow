package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.Scope

data class ImportASTNode(
    val scope: Scope,
    override val underlyingCSTNode: CSTNode
) : ASTNode()