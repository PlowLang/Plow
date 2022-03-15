package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ExpressionASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class MismatchedTypesError(
    val expectedType: IRType,
    val foundType: IRType,
    val expression: ExpressionASTNode
) : PlowError(
    "mismatched types",
    PlowIssueInfo(
        expression.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Expected the type `$expectedType` but instead found the type `$foundType`."
    )
)