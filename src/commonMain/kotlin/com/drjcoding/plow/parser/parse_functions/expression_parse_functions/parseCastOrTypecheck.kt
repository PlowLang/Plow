package com.drjcoding.plow.parser.parse_functions.expression_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.CastCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.TypecheckCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeError
import com.drjcoding.plow.parser.parse_functions.errors.ParseAssertionUnexpectedToken
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode

/**
 * Parses a cast or typecheck assuming we know one exists (i.e. `foo as bar` or `foo is bar`)
 */
fun parseCastOrTypecheck(ts: LexTokenStream, toCheck: ExpressionCSTNode): ExpressionCSTNode {
    val asOrIs = ts.popNSTokenCSTNode()
    val type = parseType(ts) ?: throw ExpectedTypeError(asOrIs)

    return when (asOrIs.token.type) {
        LexTokenType.IS -> TypecheckCSTNode(toCheck, asOrIs, type)
        LexTokenType.AS -> CastCSTNode(toCheck, asOrIs, type)
        else -> throw ParseAssertionUnexpectedToken(ts.popNSTokenCSTNode(), listOf(LexTokenType.AS, LexTokenType.IS))
    }
}