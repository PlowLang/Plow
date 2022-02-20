package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.MemberDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.MethodDeclarationASTNode
import com.drjcoding.plow.source_abstractions.SourceFileRange

interface DeclarationCSTNode {
    /**
     * The [SourceFileRange] that this node and all its children, including skipables, inhabit.
     */
    val range: SourceFileRange

    fun toASTAsFileChild(): PlowResult<FileChildASTNode>

    sealed class ObjectChildASTNode {
        class Method(val methodDeclarationASTNode: MethodDeclarationASTNode): ObjectChildASTNode()
        class Member(val memberDeclarationASTNode: MemberDeclarationASTNode): ObjectChildASTNode()
    }

    fun toASTAsObjectChild(): PlowResult<ObjectChildASTNode>
}
