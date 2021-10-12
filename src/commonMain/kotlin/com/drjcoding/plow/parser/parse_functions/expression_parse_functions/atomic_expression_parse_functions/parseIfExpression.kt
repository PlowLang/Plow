package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.IfContinuationCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.IfExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedCodeBlockError
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.parseCodeBlock
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses an if expression assuming we know one exists.
 *
 * ```
 * ifExpression ::= IF expression codeBlock (ELSE ifExpression)* (ELSE codeBlock)?
 * ```
 */
internal fun parseIfExpression(ts: LexTokenStream): IfExpressionCSTNode {
    val ifKw = ts.popNSTokenCSTNode().assertType(LexTokenType.IF)
    val condition = parseExpression(ts) ?: throw ExpectedExpressionError(ifKw.range)
    val block =
        if (ts.peekNSIsType(LexTokenType.L_CURLY)) parseCodeBlock(ts) else throw ExpectedCodeBlockError(condition.range)

    var continuation: IfContinuationCSTNode? = null
    if (ts.peekNSIsType(LexTokenType.ELSE)) {
        val elseKw = ts.popNSTokenCSTNode().assertType(LexTokenType.ELSE)
        continuation = when (ts.safePeekNS()?.type) {
            LexTokenType.IF -> IfContinuationCSTNode.ElseIfContinuation(elseKw, parseIfExpression(ts))
            LexTokenType.L_CURLY -> IfContinuationCSTNode.ElseContinuation(elseKw, parseCodeBlock(ts))
            else -> throw ExpectedCodeBlockError(elseKw.range)
        }
    }

    return IfExpressionCSTNode(ifKw, condition, block, continuation)
}
