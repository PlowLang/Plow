package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ClassDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.FunctionDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.VariableDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

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

    override fun toAST() = ClassDeclarationASTNode(
        name.token.text,
        declarations.filterIsInstance<VariableDeclarationCSTNode>().map { it.toAST() },
        declarations.filterIsInstance<FunctionDeclarationCSTNode>().map { it.toAST() },
        declarations.filter { it.type == DeclarationType.OBJECT }.map { it.toAST() },
        this
    )

    override val type = DeclarationType.OBJECT
}