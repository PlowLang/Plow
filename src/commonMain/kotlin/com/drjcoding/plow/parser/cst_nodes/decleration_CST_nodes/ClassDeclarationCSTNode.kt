package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.StatementCSTNode

/**
 * A class declaration. (ex `class foo { val i = 1 }`)
 */
data class ClassDeclarationCSTNode(
    val classKw: TokenCSTNode,
    val name: TokenCSTNode,
    val lCurly: TokenCSTNode,
    val declarations: List<DeclarationCSTNode>,
    val rCurly: TokenCSTNode,
) : CSTNode(), DeclarationCSTNode {
    override val range = classKw.range + rCurly.range
}