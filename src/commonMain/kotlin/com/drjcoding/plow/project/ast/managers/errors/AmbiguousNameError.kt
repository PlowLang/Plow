package com.drjcoding.plow.project.ast.managers.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.GlobalDeclarationASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class AmbiguousNameError(
    val givenQI: QualifiedIdentifierASTNode,
    val names: List<GlobalDeclarationASTNode>
) : PlowError(
    "ambiguous name",
    PlowIssueInfo(
        givenQI.underlyingCSTNode.range.toPlowIssueTextRange(),
        "This name resolved to multiple different values."
    ),
    names.map { PlowIssueInfo(it.underlyingCSTNode.range.toPlowIssueTextRange(), "One possible resolution.") }
)
