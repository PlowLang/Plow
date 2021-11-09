package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.EnumDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.NamespaceCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An enum declaration.
 */
data class EnumDeclarationCSTNode(
    val enumKw: TokenCSTNode,
    val name: TokenCSTNode,
    val lCurly: TokenCSTNode,
    val cases: List<EnumCaseCSTNode>,
    val declarations: List<DeclarationCSTNode>,
    val rCurly: TokenCSTNode
): DeclarationCSTNode(), NamespaceCSTNode {
    override lateinit var parentNamespace: NamespaceCSTNode

    override val range = enumKw.range + rCurly.range

    override fun toAST() = EnumDeclarationASTNode(
        name.token.text,
        parentNamespace.toNamespaceASTNode(),
        cases.map { it.name.token.text },
        declarations.filterIsInstance<FunctionDeclarationCSTNode>().map { it.toAST() },
        declarations.filter { it.type != DeclarationType.FUNCTION }.map { it.toAST() },
        this
    )

    override fun toNamespaceASTNode(): NamespaceASTNode = toAST()

    override val type = DeclarationType.OBJECT
}

/**
 * A case in an enum declaration.
 */
data class EnumCaseCSTNode(
    val name: TokenCSTNode,
    val comma: TokenCSTNode?
): CSTNode() {
    override val range = name.range + (comma ?: name).range
}
