package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseVariableASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.MemberDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.VariableDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode

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

    private fun toAST() = BaseVariableASTNode(
        name.token.text,
        typeAnnotation?.toAST(),
        value.toAST(),
        this
    )

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> =
        VariableDeclarationASTNode(toAST(), this).toPlowResult()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        DeclarationCSTNode.ObjectChildASTNode.Member(
            MemberDeclarationASTNode(toAST(), this)
        ).toPlowResult()
}

