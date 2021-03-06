package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BindingPower
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions.parseExpressionAtom

/**
 * ```
 * expression
 *   : primaryExpression
 *   | expression DOT IDENTIFIER                 // object access
 *   | expression L_SQUARE expression R_SQUARE   // array access - TODO
 *   | expression L_PAREN functionArgs R_PAREN   // function call
 *   | expression AS type                        // cast
 *   | expression IS type                        // typecheck
 *   | prefixOp expression                       // prefix operators - TODO
 *   | expression multiplicationOp expression    // multiplication level binary op
 *   | expression additionOp expression          // addition level binary op
 *   | expression comparisonOp expression        // comparison level binary op
 *   | expression equalityOp expression          // equality level binary op
 *   | expression AND expression                 // and op
 *   | expression OR expression                  // or op
 *   | <assoc=right>
 *      expression assignmentOp expression      // assignment - TODO
 *   ;
 * ```
 */
fun parseExpression(ts: LexTokenStream): ExpressionCSTNode? =
    parseSubExpression(ts, BindingPower.LOOSEST)

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
        LexTokenType.AS, LexTokenType.IS -> parseCastOrTypecheck(ts, currentExp)
        LexTokenType.ASSIGN -> parseAssignment(ts, currentExp)
        else -> when {
            next.type.isOperator -> {
                if (BindingPower.fromOp(next) looserThan tightestBindingPower) {
                    // Ex. we had 3 * 4, now we have +
                    null
                } else {
                    parseBinaryOp(ts, currentExp, tightestBindingPower)
                }
            }
            else -> null
        }
    }
}
