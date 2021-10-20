package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class CodeBlockASTNode(
    val statements: List<StatementASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode()