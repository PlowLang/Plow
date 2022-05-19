package com.drjcoding.plow.parser.parse_functions.expression_parse_functions.atomic_expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.WhileExpressionCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedCodeBlockError
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import com.drjcoding.plow.parser.parse_functions.parseCodeBlock
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

internal fun parseWhileExpression(ts: LexTokenStream): WhileExpressionCSTNode {
    val whileKw = ts.popNSTokenCSTNode().assertType(LexTokenType.WHILE)
    val condition = parseExpression(ts) ?: throw ExpectedExpressionError(whileKw.range)
    val block = if (ts.peekNSIsType(LexTokenType.L_CURLY)) parseCodeBlock(ts) else throw ExpectedCodeBlockError(condition.range)
    return WhileExpressionCSTNode(whileKw, condition, block)
}
