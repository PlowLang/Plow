package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.declaration_parse_functions.parseVariableDeclaration

/**
 * ```
 * primaryExpression
 * : L_PAREN expression R_PAREN
 * | qualifiedIdentifier
 * | literal
 * | THIS                       //TODO
 * | SUPER                      //TODO
 * | ifExpression
 * | tuple                      //TODO
 * ```
 */
internal fun parseExpressionAtom(ts: LexTokenStream): ExpressionCSTNode? {
    //TODO
    return when (ts.safePeekNS()?.type) {
        LexTokenType.IDENTIFIER -> parseVarAccess(ts)
        LexTokenType.L_PAREN -> parseParenExpression(ts)
        LexTokenType.INT_LITERAL -> parseIntLiteral(ts)
        LexTokenType.FLOAT_LITERAL -> parseFloatLiteral(ts)
        LexTokenType.RETURN -> parseReturnExpression(ts)
        LexTokenType.IF -> parseIfExpression(ts)
        LexTokenType.WHILE -> parseWhileExpression(ts)
        else -> null
    }
}

