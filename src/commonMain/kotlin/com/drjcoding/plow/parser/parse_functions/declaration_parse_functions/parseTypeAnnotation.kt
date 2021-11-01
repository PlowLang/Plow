package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.TypeAnnotationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses a [TypeAnnotationCSTNode]. Returns null if there is not one.
 */
fun parseTypeAnnotation(ts: LexTokenStream): TypeAnnotationCSTNode? =
    if (ts.peekNSIsType(LexTokenType.COLON)) {
        val colon = ts.popNSTokenCSTNode()
        val type = parseType(ts) ?: throw ExpectedTypeError(colon)
        TypeAnnotationCSTNode(colon, type)
    } else {
        null
    }