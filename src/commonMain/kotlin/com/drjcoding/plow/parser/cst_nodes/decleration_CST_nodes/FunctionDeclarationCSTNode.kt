package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.*
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.source_abstractions.toSourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

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
    val body: FunctionBodyCSTNode,
) : CSTNode(), DeclarationCSTNode {
    override val range = funcKw.range + body.range

    private fun toBaseFunctionASTNode() = BaseFunctionASTNode(
        name.token.text,
        args.map { it.toAST() },
        returnType?.toAST(),
        body.toAST(),
        this
    )

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> =
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

sealed class FunctionBodyCSTNode : CSTNode() {
    abstract fun toAST(): BaseFunctionBody

    class BlockBody(val body: CodeBlockCSTNode) : FunctionBodyCSTNode() {
        override val range = body.range

        override fun toAST(): BaseFunctionBody =
            BaseFunctionBody.BlockBody(body.toAST())

    }

    class ExternBody(val externKw: TokenCSTNode, val externName: TokenCSTNode) : FunctionBodyCSTNode() {
        override val range = externKw.range + externName.range

        override fun toAST(): BaseFunctionBody =
            BaseFunctionBody.ExternBody(externName.token.text.toUnderlyingString().let {
                it.substring(1 until it.lastIndex)
            }.toSourceString())
    }

    class ExternCodeBody(
        val externKw: TokenCSTNode,
        val lCurly: TokenCSTNode,
        val llvmCode: TokenCSTNode,
        val rCurly: TokenCSTNode
    ) : FunctionBodyCSTNode() {
        override val range = externKw.range + rCurly.range

        override fun toAST(): BaseFunctionBody =
            BaseFunctionBody.ExternCodeBody(llvmCode.token.text.toUnderlyingString().let {
                it.substring(1 until it.lastIndex)
            }.toSourceString())
    }
}