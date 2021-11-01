package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.VariableDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parses a variable declaration.
 *
 * `varDeclaration ::= (LET | VAR) IDENTIFIER ASSIGN expression`
 */
fun parseVariableDeclaration(ts: LexTokenStream): VariableDeclarationCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.VAR) && !ts.peekNSIsType(LexTokenType.LET)) return null

    val letOrVar = ts.popNSTokenCSTNode()
    val identifier = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)
    val typeAnnotation = parseTypeAnnotation(ts)
    val assign = ts.safePopNSTokenCSTNode().expectType(LexTokenType.ASSIGN)
    val value = parseExpression(ts) ?: throw ExpectedExpressionError(assign.range)

    return VariableDeclarationCSTNode(letOrVar, identifier, typeAnnotation, assign, value)
}