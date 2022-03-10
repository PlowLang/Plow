package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseVariableASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.LocalVariableDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.StatementCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.TypeAnnotationCSTNode

data class LocalVariableDeclarationCSTNode(
    val letOrVar: TokenCSTNode,
    val name: TokenCSTNode,
    val typeAnnotation: TypeAnnotationCSTNode?,
    val assign: TokenCSTNode,
    val value: ExpressionCSTNode
) : CSTNode(), StatementCSTNode {
    override val range = letOrVar.range + value.range

    override fun toAST(): StatementASTNode = LocalVariableDeclarationASTNode(
        BaseVariableASTNode(
            name.token.text,
            typeAnnotation?.toAST(),
            value.toAST(),
            this
        ),
        this
    )
}

