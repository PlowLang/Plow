package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Used when a type is expected and is not found.
 */
class ExpectedTypeError(after: CSTNode) : PlowError(
    "expected a type",
    PlowIssueInfo(after.range.toPlowIssueTextRange(), "Expected a type after this")
)