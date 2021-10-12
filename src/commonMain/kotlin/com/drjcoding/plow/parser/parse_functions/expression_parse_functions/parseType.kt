package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.NamedTypeCSTNode
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.TypeCSTNode
import com.drjcoding.plow.parser.parse_functions.parseQualifiedIdentifier

/**
 * Parses a type or returns null if there isn't one.
 */
fun parseType(ts: LexTokenStream): TypeCSTNode? {
    // TODO function types, etc.
    return parseQualifiedIdentifier(ts)?.let(::NamedTypeCSTNode)
}