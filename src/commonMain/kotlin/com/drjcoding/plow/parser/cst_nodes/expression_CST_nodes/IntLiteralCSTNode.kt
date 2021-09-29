package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An integer literal.
 */
data class IntLiteralCSTNode(val int: TokenCSTNode): ExpressionCSTNode() {
    override val range = int.range
}