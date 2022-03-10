package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class DuplicateNameInScopeError(
    val originalDefinition: ASTNode,
    val newDefinition: ASTNode
) : PlowError(
    "duplicate names in scope",
    PlowIssueInfo(
        newDefinition.underlyingCSTNode.range.toPlowIssueTextRange(),
        "This name was already defined in this scope"
    ),
    listOf(
        PlowIssueInfo(originalDefinition.underlyingCSTNode.range.toPlowIssueTextRange(), "Originally defined here.")
    )
)