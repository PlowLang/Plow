package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.ImportCSTNode

fun parseImport(ts: LexTokenStream): ImportCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.IMPORT)) return null
    val importKw = ts.popNSTokenCSTNode().assertType(LexTokenType.IMPORT)
    val toImport = parseQualifiedIdentifier(ts) ?: throw ExpectedQualifiedIdentifier(importKw)
    return ImportCSTNode(importKw, toImport)
}