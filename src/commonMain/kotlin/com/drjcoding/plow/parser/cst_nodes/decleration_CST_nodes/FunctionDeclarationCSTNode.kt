package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.FunctionDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.FunctionDeclarationArgASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

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
) : DeclarationCSTNode() {
    override val range = funcKw.range + body.range

    override fun toAST() = FunctionDeclarationASTNode(
        name.token.text,
        args.map { it.toAST() },
        returnType?.toAST(),
        body.toAST(),
        this
    )

    override val type = DeclarationType.FUNCTION
}

/**
 * An argument in a function declaration. (ex `foo: Int,`)
 */
data class FunctionDeclarationArgCSTNode(
    val name: TokenCSTNode,
    val type: TypeAnnotationCSTNode,
    val comma: TokenCSTNode?
) : CSTNode() {
    override val range = name.range + (comma ?: type).range

    fun toAST() = FunctionDeclarationArgASTNode(
        name.token.text,
        type.toAST(),
        this
    )
}