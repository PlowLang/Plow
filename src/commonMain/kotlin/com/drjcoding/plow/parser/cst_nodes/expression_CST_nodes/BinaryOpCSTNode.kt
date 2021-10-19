package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.BinaryOpASTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * A binary op expression. (Ex. `foo + bar`)
 *
 * @property left the left expression
 * @property op the operator
 * @property right the right expression
 */
data class BinaryOpCSTNode(
    val left: ExpressionCSTNode,
    val op: TokenCSTNode,
    val right: ExpressionCSTNode
): ExpressionCSTNode() {
    override val range = left.range + right.range

    override fun toAST() = BinaryOpASTNode(
        left.toAST(),
        op.token.text,
        right.toAST(),
        this
    )
}

class InvalidBinaryOperatorError(op: String): Exception("Invalid binary operator $op")