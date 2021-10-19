package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.TypecheckASTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.TypeCSTNode

/**
 * A typecheck (ex `foo is bar`)
 */
data class TypecheckCSTNode(val expr: ExpressionCSTNode, val isKw: TokenCSTNode, val type: TypeCSTNode) :
    ExpressionCSTNode() {
    override val range = expr.range + type.range

    override fun toAST() = TypecheckASTNode(
        expr.toAST(),
        type.toAST(),
        this
    )
}