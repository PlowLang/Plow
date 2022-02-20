package com.drjcoding.plow.project.ast.managers.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class NoMatchingTypeNameError(
    val givenQI: QualifiedIdentifierASTNode
) : PlowError(
    "no matching type name",
    PlowIssueInfo(
        givenQI.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Could not find a matching type name in this scope."
    )
)