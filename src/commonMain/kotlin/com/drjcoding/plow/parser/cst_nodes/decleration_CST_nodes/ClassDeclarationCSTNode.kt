package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ClassDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.FunctionDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.VariableDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.NamespaceCSTNode
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
) : DeclarationCSTNode(), NamespaceCSTNode {
    override lateinit var parentNamespace: NamespaceCSTNode

    override val range = classKw.range + rCurly.range

    override fun toAST() = ClassDeclarationASTNode(
        name.token.text,
        parentNamespace.toNamespaceASTNode(),
        declarations.filterIsInstance<VariableDeclarationCSTNode>().map { it.toAST() },
        declarations.filterIsInstance<FunctionDeclarationCSTNode>().map { it.toAST() },
        declarations.filter { it.type == DeclarationType.OBJECT }.map { it.toAST() },
        this
    )

    override fun toNamespaceASTNode(): NamespaceASTNode = toAST()

    override val type = DeclarationType.OBJECT
}