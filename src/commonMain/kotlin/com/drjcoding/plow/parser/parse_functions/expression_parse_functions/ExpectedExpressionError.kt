package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Used when an expression is syntactically expected but none is found. (Ex. `foo + `)
 *
 * @param location the location after which the expression is expected.
 */
class ExpectedExpressionError(
    location: SourceFileRange
): PlowError(
    "expected expression",
    PlowIssueInfo(location.toPlowIssueTextRange(), "expected an expression after this")
)