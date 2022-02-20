package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class NotAValidObjectChildError(
    parentType: String,
    declarationType: String,
    location: SourceFileRange
): PlowError(
    "invalid child in declaration",
    PlowIssueInfo(location.toPlowIssueTextRange(), "A $declarationType is not allowed in a $parentType.")
)