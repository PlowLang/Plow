package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ExpressionASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class CannotAssignToThisExpression(
    val expression: ExpressionASTNode
) : PlowError(
    "cannot assign to this expression",
    PlowIssueInfo(
        expression.underlyingCSTNode.range.toPlowIssueTextRange(),
        "This type of expression cannot be assigned to."
    )
)