package com.drjcoding.plow.project.ast.managers.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class NoMatchingNameError(
    val givenQI: QualifiedIdentifierASTNode
) : PlowError(
    "no matching name",
    PlowIssueInfo(
        givenQI.underlyingCSTNode.range.toPlowIssueTextRange(),
        "Could not find the name `${givenQI.prettyPrint()}` in this scope."
    )
)