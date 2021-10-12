package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.StatementCSTNode

/**
 * A variable declaration using let or var (ex `let a = 1`)
 */
data class VariableDeclarationCSTNode(
    val letOrVar: TokenCSTNode,
    val name: TokenCSTNode,
    val typeAnnotation: TypeAnnotationCSTNode?,
    val assign: TokenCSTNode,
    val value: ExpressionCSTNode
) : CSTNode(), DeclarationCSTNode {
    override val range = letOrVar.range + value.range
}

