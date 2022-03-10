package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.LocalVariableDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.declaration_parse_functions.parseTypeAnnotation
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression

fun parseLocalVariableDeclaration(ts: LexTokenStream): LocalVariableDeclarationCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.VAR) && !ts.peekNSIsType(LexTokenType.LET)) return null

    val letOrVar = ts.popNSTokenCSTNode()
    val identifier = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)
    val typeAnnotation = parseTypeAnnotation(ts)
    val assign = ts.safePopNSTokenCSTNode().expectType(LexTokenType.ASSIGN)
    val value = parseExpression(ts) ?: throw ExpectedExpressionError(assign.range)

    return LocalVariableDeclarationCSTNode(letOrVar, identifier, typeAnnotation, assign, value)
}