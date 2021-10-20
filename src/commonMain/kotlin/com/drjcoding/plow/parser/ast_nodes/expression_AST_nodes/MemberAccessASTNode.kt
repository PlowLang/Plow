package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class MemberAccessASTNode(
    val value: ExpressionASTNode,
    val member: SourceString,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode()