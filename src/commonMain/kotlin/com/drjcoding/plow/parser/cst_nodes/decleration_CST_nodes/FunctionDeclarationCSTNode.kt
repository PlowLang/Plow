package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.StatementCSTNode

/**
 * A function declaration. (ex `func foo(a: Int): Int { return a * 2 }`)
 */
data class FunctionDeclarationCSTNode(
    val funcKw: TokenCSTNode,
    val name: TokenCSTNode,
    val lParen: TokenCSTNode,
    val args: List<FunctionDeclarationArgCSTNode>,
    val rParen: TokenCSTNode,
    val returnType: TypeAnnotationCSTNode?,
    val body: CodeBlockCSTNode,
) : CSTNode(), StatementCSTNode {
    override val range = funcKw.range + body.range
}

/**
 * An argument in a function declaration. (ex `foo: Int,`
 */
data class FunctionDeclarationArgCSTNode(
    val name: TokenCSTNode,
    val type: TypeAnnotationCSTNode,
    val comma: TokenCSTNode?
) : CSTNode() {
    override val range = name.range + (comma ?: type).range
}