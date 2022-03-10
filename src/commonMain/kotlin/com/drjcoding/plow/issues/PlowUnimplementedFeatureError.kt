package com.drjcoding.plow.issues

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class PlowUnimplementedFeatureError(
    val cstNode: CSTNode
) : PlowError(
    "unimplemented feature",
    PlowIssueInfo(cstNode.range.toPlowIssueTextRange(), "This feature has not yet been implemented")
)