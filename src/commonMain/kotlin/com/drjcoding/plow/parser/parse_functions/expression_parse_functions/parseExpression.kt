package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BindingPower
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.source_abstractions.toUnderlyingString

/**
 * ```
 * expression
 *   : primaryExpression
 *   | expression DOT IDENTIFIER                 // object access
 *  x| expression L_SQUARE expression R_SQUARE   // array access
 *   | expression L_PAREN functionArgs R_PAREN   // function call
 *  x| expression AS type                        // cast
 *  x| prefixOp expression                       // prefix operators
 *   | expression multiplicationOp expression    // multiplication level binary op
 *   | expression additionOp expression          // addition level binary op
 *   | expression comparisonOp expression        // comparison level binary op
 *  X| expression IS type                        // typecheck
 *   | expression equalityOp expression          // equality level binary op
 *   | expression AND expression                 // and op
 *   | expression OR expression                  // or op
 *   | <assoc=right>
 *  x   expression assignmentOp expression      // assignment
 *   ;
 * ```
 */
fun parseExpression(ts: LexTokenStream): ExpressionCSTNode? = parseSubExpression(ts, BindingPower.LOOSEST)

/**
 * Parses only expressions that bind at least as tight as [tightestBindingPower].
 */
internal fun parseSubExpression(ts: LexTokenStream, tightestBindingPower: BindingPower): ExpressionCSTNode? {
    var currentExp = parseExpressionAtom(ts) ?: return null
    while (true) {
        currentExp = parseExpressionContinuation(ts, currentExp, tightestBindingPower) ?: return currentExp
    }
}

/**
 * Takes an expression and continues it with things like operators, member access, and function calls.
 */
private fun parseExpressionContinuation(
    ts: LexTokenStream,
    currentExp: ExpressionCSTNode,
    tightestBindingPower: BindingPower
): ExpressionCSTNode? {
    val next = ts.safePeekNS() ?: return null
    return when (next.type) {
        LexTokenType.PERIOD -> parseMemberAccess(ts, currentExp)
        LexTokenType.L_PAREN -> parseFunctionCall(ts, currentExp)
        LexTokenType.OPERATOR -> {
            if (BindingPower.fromOp(next.text.toUnderlyingString()) looserThan tightestBindingPower) {
                // Ex. we had 3 * 4 and now we have +
                null
            } else {
                parseBinaryOp(ts, currentExp, tightestBindingPower)
            }
        }
        else -> null
    }
}
