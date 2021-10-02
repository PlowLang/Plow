package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.VariableAccessCSTNode
import com.drjcoding.plow.parser.parse_functions.parseQualifiedIdentifier

/**
 * Parse a variable access expression assuming we know one exists.
 *
 * `varAccess ::= qualifiedIdentifier`
 */
internal fun parseVarAccess(ts: LexTokenStream): VariableAccessCSTNode =
    VariableAccessCSTNode(parseQualifiedIdentifier(ts)!!)