package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.FunctionCallASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class CannotInvokeNonFunctionTypeError(
    val astNode: FunctionCallASTNode,
    val actualType: IRType
) : PlowError(
    "cannot invoke non function type",
    PlowIssueInfo(
        astNode.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Attempted to invoke a value with the type `$actualType`."
    )
)