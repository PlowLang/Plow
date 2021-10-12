package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.TypeCSTNode

/**
 * A typecast (ex `foo as bar`)
 */
data class CastCSTNode(val expr: ExpressionCSTNode, val asKw: TokenCSTNode, val type: TypeCSTNode) :
    ExpressionCSTNode() {
    override val range = expr.range + type.range
}