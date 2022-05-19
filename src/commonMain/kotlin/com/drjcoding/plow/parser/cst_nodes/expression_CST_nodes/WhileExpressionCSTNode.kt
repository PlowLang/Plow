package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.WhileExpressionASTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

data class WhileExpressionCSTNode(
    val whileKw: TokenCSTNode,
    val condition: ExpressionCSTNode,
    val block: CodeBlockCSTNode,
) : ExpressionCSTNode() {
    override val range = whileKw.range + block.range

    override fun toAST() = WhileExpressionASTNode(
        condition.toAST(),
        block.toAST(),
        this
    )
}
