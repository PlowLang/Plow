package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class ExpectedExpressionError(
    location: SourceFileRange
): PlowError(
    "expected expression",
    PlowIssueInfo(location.toPlowIssueTextRange(), "expected an expression after this")
)