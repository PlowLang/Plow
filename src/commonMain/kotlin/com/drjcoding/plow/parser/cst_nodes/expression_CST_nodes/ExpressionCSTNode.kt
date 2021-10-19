package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ExpressionASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.StatementCSTNode

abstract class ExpressionCSTNode : CSTNode(), StatementCSTNode {
    abstract override fun toAST(): ExpressionASTNode
}
