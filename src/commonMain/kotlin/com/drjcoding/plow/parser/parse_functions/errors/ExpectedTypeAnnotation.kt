package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Used when a type annotation is syntactically expected but none is found. (Ex. `func foo(a`)
 */
class ExpectedTypeAnnotationError(
    after: CSTNode
) : PlowError(
    "expected type annotation",
    PlowIssueInfo(after.range.toPlowIssueTextRange(), "expected a type annotation after this")
)
