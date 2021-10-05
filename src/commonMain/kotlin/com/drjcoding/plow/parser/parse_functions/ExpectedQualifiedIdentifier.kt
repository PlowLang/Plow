package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Used when a qualified identifier is expected.
 */
class ExpectedQualifiedIdentifier(after: CSTNode) : PlowError(
    "expected qualified identifier",
    PlowIssueInfo(after.range.toPlowIssueTextRange(), "Expected a qualified identifier after this")
)
