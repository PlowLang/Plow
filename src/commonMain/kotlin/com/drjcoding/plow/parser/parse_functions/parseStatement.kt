package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.StatementCSTNode
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseDeclaration
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseFunctionDeclaration
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseVariableDeclaration

/**
 * Parses an expression or returns null if none exists. This does not include any statement separator.
 */
fun parseStatement(ts: LexTokenStream): StatementCSTNode? =
    parseExpression(ts) ?: parseDeclaration(ts)
