package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * A float literal.
 */
data class FloatLiteralCSTNode(val float: TokenCSTNode): ExpressionCSTNode() {
    override val range = float.range
}
