package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.TypeCSTNode

/**
 * A type annotation in a [VariableDeclarationCSTNode]. (ex `: Int`)
 */
data class TypeAnnotationCSTNode(val colon: TokenCSTNode, val type: TypeCSTNode) : CSTNode() {
    override val range = colon.range + type.range

    fun toAST() = type.toAST()
}