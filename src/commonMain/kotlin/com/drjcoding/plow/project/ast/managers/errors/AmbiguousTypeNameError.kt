package com.drjcoding.plow.project.ast.managers.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.TypeDeclarationASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class AmbiguousTypeNameError(
    val givenQI: QualifiedIdentifierASTNode,
    val types: List<TypeDeclarationASTNode>
) : PlowError(
    "ambiguous type name",
    PlowIssueInfo(
        givenQI.underlyingCSTNode.range.toPlowIssueTextRange(),
        "This name resolved to multiple different types."
    ),
    types.map { PlowIssueInfo(it.underlyingCSTNode.range.toPlowIssueTextRange(), "One possible resolution.") }
)