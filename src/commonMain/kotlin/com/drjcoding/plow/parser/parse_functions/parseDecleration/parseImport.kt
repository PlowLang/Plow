package com.drjcoding.plow.parser.parse_functions.parseDecleration

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.ImportCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedQualifiedIdentifier
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.parseQualifiedIdentifier
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

fun parseImport(ts: LexTokenStream): ImportCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.IMPORT)) return null
    val importKw = ts.popNSTokenCSTNode().assertType(LexTokenType.IMPORT)
    val toImport = parseQualifiedIdentifier(ts) ?: throw ExpectedQualifiedIdentifier(importKw)
    return ImportCSTNode(importKw, toImport)
}