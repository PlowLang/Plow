package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ClassDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.errors.NotAValidObjectChildError

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

    private fun toAST(): PlowResult<ClassDeclarationASTNode> {
        val childrenResult = declarations.map { it.toASTAsObjectChild() }.flattenToPlowResult()
        return childrenResult.map { children ->
            ClassDeclarationASTNode(
                name.token.text,
                children.filterIsInstance<DeclarationCSTNode.ObjectChildASTNode.Method>().map { it.methodDeclarationASTNode },
                children.filterIsInstance<DeclarationCSTNode.ObjectChildASTNode.Member>().map { it.memberDeclarationASTNode },
                this
            )
        }
    }

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> = toAST()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        PlowResult.Error(NotAValidObjectChildError("object", "class", this.range))

}
