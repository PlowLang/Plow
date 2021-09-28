package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.QINamespaceCSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * `QualifiedIdentifier ::= (IDENTIFIER DOUBLE_COLON)* IDENTIFIER`
 */
fun parseQualifiedIdentifier(ts: LexTokenStream): QualifiedIdentifierCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.IDENTIFIER)) return null

    val namespaces: MutableList<QINamespaceCSTNode> = mutableListOf()
    var previousIdentifier = ts.popNSTokenCSTNode()

    while (ts.peekNSIsType(LexTokenType.DOUBLE_COLON)) {
        val colon = ts.popNSTokenCSTNode()
        namespaces.add(QINamespaceCSTNode(previousIdentifier, colon))

        if (ts.safePeekNS()?.type != LexTokenType.IDENTIFIER) throw NoIdentifierAfterColonInQIError(colon.range)
        previousIdentifier = ts.popNSTokenCSTNode()
    }

    return QualifiedIdentifierCSTNode(namespaces, previousIdentifier)
}

/**
 * Thrown when an identifier is missing in a qualified identifier (ex. `foo::bar::`). Pass in the final double colon.
 */
class NoIdentifierAfterColonInQIError(
    colon: SourceFileRange
) : PlowError(
    "no identifier after colon in qualified identifier",
    PlowIssueInfo(colon.toPlowIssueTextRange(), "expected an identifier after this colon")
)