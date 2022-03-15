package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class TypeAnnotationRequiredError(
    val variable: CSTNode // FIXME this is a little hacky
) : PlowError(
    "expected a type annotation",
    PlowIssueInfo(variable.range.toPlowIssueTextRange(), "Expected a type annotation here."),
    listOf(
        PlowIssueInfo(null, "Variable type inference is not yet supported.")
    )
)