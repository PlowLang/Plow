package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.IfExpressionASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An if expression.
 */
data class IfExpressionCSTNode(
    val ifKw: TokenCSTNode,
    val condition: ExpressionCSTNode,
    val block: CodeBlockCSTNode,
    val continuation: IfContinuationCSTNode?
) : ExpressionCSTNode() {
    override val range = ifKw.range + (continuation?.range ?: block.range)

    override fun toAST() = IfExpressionASTNode(
        condition.toAST(),
        block.toAST(),
        continuation?.toAST(),
        this
    )
}

/**
 * Anything that follows the first code block in an if expression.
 *
 * `if condition { } <something-here>`
 */
sealed class IfContinuationCSTNode : CSTNode() {

    abstract fun toAST(): CodeBlockASTNode

    /**
     * An else with no condition.
     */
    data class ElseContinuation(
        val elseKw: TokenCSTNode,
        val block: CodeBlockCSTNode
    ) : IfContinuationCSTNode() {
        override val range = elseKw.range + block.range

        override fun toAST() = block.toAST()
    }

    /**
     * An else if.
     */
    data class ElseIfContinuation(
        val elseKw: TokenCSTNode,
        val ifExpression: IfExpressionCSTNode
    ) : IfContinuationCSTNode() {
        override val range = elseKw.range + ifExpression.range

        override fun toAST() = CodeBlockASTNode(
            listOf(ifExpression.toAST()),
            this
        )
    }

}