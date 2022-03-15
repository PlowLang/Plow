package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.ir.type.FunctionIRType
import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.FunctionCallASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class MismatchedNumberOfArgumentsError(
    val functionCall: FunctionCallASTNode,
    val functionType: FunctionIRType
): PlowError(
    "mismatched number of arguments",
    PlowIssueInfo(
        functionCall.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Expected `${functionType.argumentTypes.size}` argument(s) but `${functionCall.arguments.size}` were provided."
    ),
    listOf(
        PlowIssueInfo(null, "The type of the function is `$functionType`.")
    )
)