package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.FunctionArgumentCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.FunctionCallCSTNode
import com.drjcoding.plow.parser.parse_functions.assertType
import com.drjcoding.plow.parser.parse_functions.expectType
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parses a function call assuming one is already known to exist.
 *
 * `functionCall ::= expression L_PAREN (expression COMMA)* expression? R_PAREN`
 */
fun parseFunctionCall(ts: LexTokenStream, function: ExpressionCSTNode): FunctionCallCSTNode {
    val lParen = ts.popNSTokenCSTNode().assertType(LexTokenType.L_PAREN)

    val args: MutableList<FunctionArgumentCSTNode> = mutableListOf()
    while (true) {
        // TODO - We should instead check for a closing ) first which lets us give a more helpful expected expression
        //  error instead of an expected token ) error.

        val arg = parseExpression(ts) ?: break
        val isComma = ts.peekNSIsType(LexTokenType.COMMA)
        if (isComma) {
            args.add(FunctionArgumentCSTNode(arg, ts.popNSTokenCSTNode().assertType(LexTokenType.COMMA)))
        } else {
            args.add(FunctionArgumentCSTNode(arg, null))
            break
        }
    }

    val rParen = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_PAREN)

    return FunctionCallCSTNode(function, lParen, args, rParen)
}