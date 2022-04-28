package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.ClassDeclarationCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.ExternClassDeclarationCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.UserClassDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parses a class declaration. (ex `class Foo { let a = 1 }`)
 */
fun parseClassDeclaration(ts: LexTokenStream): ClassDeclarationCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.CLASS)) return null

    val classKw = ts.popNSTokenCSTNode().assertType(LexTokenType.CLASS)
    val name = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)

    val next = ts.safePopNSTokenCSTNode()

    return if (next?.token?.type == LexTokenType.EXTERN) {
        val externKW = next.expectType(LexTokenType.EXTERN)
        val externName = ts.safePopNSTokenCSTNode().expectType(LexTokenType.STRING_LITERAL)

        ExternClassDeclarationCSTNode(classKw, name, externKW, externName)
    } else {
        val lCurly = next.expectType(LexTokenType.L_CURLY)
        val declarations = parseDeclarations(ts)
        val rCurly = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_CURLY)

        UserClassDeclarationCSTNode(classKw, name, lCurly, declarations, rCurly)
    }
}