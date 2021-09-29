package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.VariableAccessCSTNode
import com.drjcoding.plow.parser.parse_functions.parseQualifiedIdentifier

/**
 * ```
 * primaryExpression
 * : L_PAREN expression R_PAREN //TODO
 * | qualifiedIdentifier
 * | literal                    //TODO
 * | THIS                       //TODO
 * | SUPER                      //TODO
 * | ifExpression               //TODO
 * | tuple                      //TODO
 * ```
 */
internal fun parseExpressionAtom(ts: LexTokenStream): ExpressionCSTNode? {
    //TODO
    return if (ts.peekNSIsType(LexTokenType.IDENTIFIER)) {
        VariableAccessCSTNode(parseQualifiedIdentifier(ts)!!)
    } else {
        null
    }
}