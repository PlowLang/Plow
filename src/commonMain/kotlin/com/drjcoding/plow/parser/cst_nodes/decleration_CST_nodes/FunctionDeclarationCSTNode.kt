package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseFunctionASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseFunctionArgASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.FunctionDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.MethodDeclarationASTNode
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
) : CSTNode(), DeclarationCSTNode {
    override val range = funcKw.range + body.range

    private fun toBaseFunctionASTNode() = BaseFunctionASTNode(
        name.token.text,
        args.map { it.toAST() },
        returnType?.toAST(),
        body.toAST(),
        this
    )

    override fun toASTAsFileChild(): PlowResult<DeclarationCSTNode.FileChildASTNode> =
        FunctionDeclarationASTNode(toBaseFunctionASTNode(), this).toPlowResult()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        DeclarationCSTNode.ObjectChildASTNode.Method(
            MethodDeclarationASTNode(toBaseFunctionASTNode(), this)
        ).toPlowResult()

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

    fun toAST() = BaseFunctionArgASTNode(
        name.token.text,
        type.toAST(),
        this
    )
}