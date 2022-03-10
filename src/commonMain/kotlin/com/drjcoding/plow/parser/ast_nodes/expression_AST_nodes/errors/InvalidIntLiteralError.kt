package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.IntLiteralASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class InvalidIntLiteralError(
    val node: IntLiteralASTNode
) : PlowError(
    "invalid integer literal",
    PlowIssueInfo(node.underlyingCSTNode.range.toPlowIssueTextRange(), "Could not convert this to an integer.")
)