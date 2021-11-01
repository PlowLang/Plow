package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.EnumCaseCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.EnumDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parse an enum declaration.
 */
fun parseEnumDeclaration(ts: LexTokenStream): EnumDeclarationCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.ENUM)) return null

    val enumKw = ts.popNSTokenCSTNode().assertType(LexTokenType.ENUM)
    val name = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)
    val lCurly = ts.safePopNSTokenCSTNode().expectType(LexTokenType.L_CURLY)
    val cases: MutableList<EnumCaseCSTNode> = mutableListOf()
    while (true) {
        val nextCase = parseEnumCase(ts) ?: break
        cases.add(nextCase)
        if (nextCase.comma == null) break
    }
    val declarations = parseDeclarations(ts)
    val rCurly = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_CURLY)

    return EnumDeclarationCSTNode(enumKw, name, lCurly, cases, declarations, rCurly)
}

/**
 * Parse an enum case.
 */
fun parseEnumCase(ts: LexTokenStream): EnumCaseCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.IDENTIFIER)) return null

    val name = ts.popNSTokenCSTNode().assertType(LexTokenType.IDENTIFIER)
    val comma = if (ts.peekNSIsType(LexTokenType.COMMA)) ts.popNSTokenCSTNode() else null

    return EnumCaseCSTNode(name, comma)
}