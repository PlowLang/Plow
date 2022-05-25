package com.drjcoding.plow.parser.parse_functions.declaration_parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.LexTokenType
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.FunctionBodyCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.FunctionDeclarationArgCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.FunctionDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedCodeBlockError
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeAnnotationError
import com.drjcoding.plow.parser.parse_functions.errors.assertType
import com.drjcoding.plow.parser.parse_functions.errors.expectType
import com.drjcoding.plow.parser.parse_functions.parseCodeBlock
import com.drjcoding.plow.parser.parse_functions.popNSTokenCSTNode
import com.drjcoding.plow.parser.parse_functions.safePopNSTokenCSTNode

/**
 * Parses a [FunctionDeclarationCSTNode] or returns null if none exists.
 */
fun parseFunctionDeclaration(ts: LexTokenStream): FunctionDeclarationCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.FUNC)) return null

    val funcKw = ts.popNSTokenCSTNode().assertType(LexTokenType.FUNC)
    val name = ts.safePopNSTokenCSTNode().expectType(LexTokenType.IDENTIFIER)
    val lParen = ts.safePopNSTokenCSTNode().expectType(LexTokenType.L_PAREN)
    val args: MutableList<FunctionDeclarationArgCSTNode> = mutableListOf()
    while (true) {
        val arg = parseFunctionDeclarationArg(ts) ?: break
        args.add(arg)
        if (arg.comma == null) break
    }
    val rParen = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_PAREN)
    val returnType = parseTypeAnnotation(ts)
    val body = if (ts.peekNSIsType(LexTokenType.L_CURLY)) {
        FunctionBodyCSTNode.BlockBody(parseCodeBlock(ts))
    } else if (ts.peekNSIsType(LexTokenType.EXTERN)) {
        val externKw = ts.popNSTokenCSTNode().assertType(LexTokenType.EXTERN)

        if (ts.peekNSIsType(LexTokenType.STRING_LITERAL)) {
            val externName = ts.popNSTokenCSTNode().expectType(LexTokenType.STRING_LITERAL)
            FunctionBodyCSTNode.ExternBody(externKw, externName)
        } else {
            val lCurly = ts.safePopNSTokenCSTNode().expectType(LexTokenType.L_CURLY)
            val code = ts.safePopNSTokenCSTNode().expectType(LexTokenType.STRING_LITERAL)
            val rCurly = ts.safePopNSTokenCSTNode().expectType(LexTokenType.R_CURLY)
            FunctionBodyCSTNode.ExternCodeBody(externKw, lCurly, code, rCurly)
        }

    } else {
        throw ExpectedCodeBlockError((returnType ?: rParen).range)
    }

    return FunctionDeclarationCSTNode(funcKw, name, lParen, args, rParen, returnType, body)
}

private fun parseFunctionDeclarationArg(ts: LexTokenStream): FunctionDeclarationArgCSTNode? {
    if (!ts.peekNSIsType(LexTokenType.IDENTIFIER)) return null
    val name = ts.popNSTokenCSTNode().assertType(LexTokenType.IDENTIFIER)
    val typeAnnotation = parseTypeAnnotation(ts) ?: throw ExpectedTypeAnnotationError(name)
    val comma = if (ts.peekNSIsType(LexTokenType.COMMA)) ts.popNSTokenCSTNode() else null
    return FunctionDeclarationArgCSTNode(name, typeAnnotation, comma)
}