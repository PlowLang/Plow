package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ReturnExpressionASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class IncorrectReturnTypeError(
    val returnExpression: ReturnExpressionASTNode,
    val expectedType: IRType,
    val foundType: IRType
) : PlowError(
    "incorrect return type",
    PlowIssueInfo(
        returnExpression.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Expected the type `$expectedType` but found `$foundType`."
    )
)