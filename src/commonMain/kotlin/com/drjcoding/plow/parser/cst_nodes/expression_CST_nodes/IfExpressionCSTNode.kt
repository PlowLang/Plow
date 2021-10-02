package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

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
): ExpressionCSTNode() {
    override val range = ifKw.range + (continuation?.range ?: block.range)
}

/**
 * Anything that follows the first code block in an if expression.
 *
 * `if condition { } <something-here>`
 */
sealed class IfContinuationCSTNode: CSTNode() {

    /**
     * An else with no condition.
     */
    data class ElseContinuation(
        val elseKw: TokenCSTNode,
        val codeBlockCSTNode: CodeBlockCSTNode
    ): IfContinuationCSTNode() {
        override val range = elseKw.range + codeBlockCSTNode.range
    }

    /**
     * An else if.
     */
    data class ElseIfContinuation(
        val elseKw: TokenCSTNode,
        val ifExpression: IfExpressionCSTNode
    ): IfContinuationCSTNode() {
        override val range = elseKw.range + ifExpression.range
    }

}