package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.EnumCaseASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.EnumDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.errors.NotAValidObjectChildError

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
): CSTNode(), DeclarationCSTNode {
    override val range = enumKw.range + rCurly.range

    private fun toAST(): PlowResult<EnumDeclarationASTNode> {
        val childrenResult = declarations.map { it.toASTAsObjectChild() }.flattenToPlowResult()
        val cases = cases.map { EnumCaseASTNode(it.name.token.text, it) }
        return childrenResult.map { children ->
            val enum = EnumDeclarationASTNode(
                name.token.text,
                cases,
                children.filterIsInstance<DeclarationCSTNode.ObjectChildASTNode.Method>().map { it.methodDeclarationASTNode },
                this
            )
            cases.forEach { it.parentEnum = enum }
            enum
        }
    }

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> = toAST()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        PlowResult.Error(NotAValidObjectChildError("object", "enum", this.range))
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
