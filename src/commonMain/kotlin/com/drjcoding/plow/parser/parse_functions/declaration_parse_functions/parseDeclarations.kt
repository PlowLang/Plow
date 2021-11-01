package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.DeclarationCSTNode

/**
 * Parses a series of declarations one after another.
 */
fun parseDeclarations(ts: LexTokenStream): List<DeclarationCSTNode> {
    val declarations: MutableList<DeclarationCSTNode> = mutableListOf()
    while (true) declarations.add(parseDeclaration(ts) ?: break)
    return declarations
}

fun parseDeclaration(ts: LexTokenStream): DeclarationCSTNode? =
    parseVariableDeclaration(ts)
        ?: parseFunctionDeclaration(ts)
        ?: parseClassDeclaration(ts)
        ?: parseEnumDeclaration(ts)