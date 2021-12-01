package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.VariableDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.NamespaceCSTNode
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
) : DeclarationCSTNode() {
    override lateinit var parentNamespace: NamespaceCSTNode

    override val range = letOrVar.range + value.range

    override fun toAST() = VariableDeclarationASTNode(
        name.token.text,
        typeAnnotation?.toAST(),
        value.toAST(),
        this
    )

    override val type = DeclarationType.VARIABLE
}

