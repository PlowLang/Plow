package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ClassDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ExternClassDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.UserClassDeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.errors.NotAValidObjectChildError
import com.drjcoding.plow.source_abstractions.toSourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

abstract class ClassDeclarationCSTNode : CSTNode(), DeclarationCSTNode {
    abstract val classKw: TokenCSTNode
    abstract val name: TokenCSTNode
}

/**
 * A class declaration. (ex `class foo { val i = 1 }`)
 */
data class UserClassDeclarationCSTNode(
    override val classKw: TokenCSTNode,
    override val name: TokenCSTNode,
    val lCurly: TokenCSTNode,
    val declarations: List<DeclarationCSTNode>,
    val rCurly: TokenCSTNode,
) : ClassDeclarationCSTNode() {
    override val range = classKw.range + rCurly.range

    private fun toAST(): PlowResult<ClassDeclarationASTNode> {
        val childrenResult = declarations.map { it.toASTAsObjectChild() }.flattenToPlowResult()
        return childrenResult.map { children ->
            UserClassDeclarationASTNode(
                name.token.text,
                children.filterIsInstance<DeclarationCSTNode.ObjectChildASTNode.Method>()
                    .map { it.methodDeclarationASTNode },
                children.filterIsInstance<DeclarationCSTNode.ObjectChildASTNode.Member>()
                    .map { it.memberDeclarationASTNode },
                this
            )
        }
    }

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> = toAST()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        PlowResult.Error(NotAValidObjectChildError("object", "class", this.range))

}

data class ExternClassDeclarationCSTNode(
    override val classKw: TokenCSTNode,
    override val name: TokenCSTNode,
    val externKw: TokenCSTNode,
    val externName: TokenCSTNode,
) : ClassDeclarationCSTNode() {
    override val range = classKw.range + externName.range

    private fun toAST(): PlowResult<ClassDeclarationASTNode> =
        ExternClassDeclarationASTNode(
            name.token.text,
            externName.token.text.toUnderlyingString().let {
                it.substring(1 until it.lastIndex)
            }.toSourceString(),
            this
        ).toPlowResult()

    override fun toASTAsFileChild(): PlowResult<FileChildASTNode> = toAST()

    override fun toASTAsObjectChild(): PlowResult<DeclarationCSTNode.ObjectChildASTNode> =
        PlowResult.Error(NotAValidObjectChildError("object", "class", this.range))
}