package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.*
import com.drjcoding.plow.parser.parse_functions.*

/**
 * ```
 * primaryExpression
 * : L_PAREN expression R_PAREN
 * | qualifiedIdentifier
 * | literal
 * | THIS                       //TODO
 * | SUPER                      //TODO
 * | ifExpression               //TODO
 * | tuple                      //TODO
 * ```
 */
internal fun parseExpressionAtom(ts: LexTokenStream): ExpressionCSTNode? {
    //TODO
    return when (ts.peekNS().type) {
        LexTokenType.IDENTIFIER -> parseVarAccess(ts)
        LexTokenType.L_PAREN -> parseParenExpression(ts)
        LexTokenType.INT_LITERAL -> parseIntLiteral(ts)
        LexTokenType.FLOAT_LITERAL -> parseFloatLiteral(ts)
        else -> null
    }
}

/**
 * Parses a int literal assuming we know one exists.
 */
private fun parseIntLiteral(ts: LexTokenStream): IntLiteralCSTNode =
    IntLiteralCSTNode(ts.popNSTokenCSTNode())


/**
 * Parses a float literal assuming we know one exists.
 */
private fun parseFloatLiteral(ts: LexTokenStream): FloatLiteralCSTNode =
    FloatLiteralCSTNode(ts.popNSTokenCSTNode())

/**
 * Parse a variable access expression assuming we know one exists.
 *
 * `varAccess ::= qualifiedIdentifier`
 */
private fun parseVarAccess(ts: LexTokenStream): VariableAccessCSTNode =
    VariableAccessCSTNode(parseQualifiedIdentifier(ts)!!)

/**
 * Parse a parenthesized expression assuming we know one exists.
 *
 * `parenExpression ::= L_PAREN expression R_PAREN
 */
private fun parseParenExpression(ts: LexTokenStream): ParenthesizedExpressionCSTNode {
    val lParen = ts.popNSTokenCSTNode().assertType(LexTokenType.L_PAREN)
    val expression = parseExpression(ts) ?: throw ExpectedExpressionError(lParen.range)
    val rParen = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_PAREN)
    return ParenthesizedExpressionCSTNode(lParen, expression, rParen)
}

