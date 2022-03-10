package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.StatementCSTNode
import com.drjcoding.plow.parser.parse_functions.declaration_parse_functions.parseDeclaration
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression

/**
 * Parses an expression or returns null if none exists. This does not include any statement separator.
 */
fun parseStatement(ts: LexTokenStream): StatementCSTNode? =
    parseExpression(ts)
//        ?: parseDeclaration(ts)
        ?: parseLocalVariableDeclaration(ts)
