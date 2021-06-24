package lexer

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.*
import com.drjcoding.plow.source_abstractions.SourceFileLocation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LexTests {

    @Test
    fun testKeywordLexes() {
        listOf(
            "var" to LexTokenType.VAR,
            "let" to LexTokenType.LET,
            "if" to LexTokenType.IF,
            "else" to LexTokenType.ELSE,
            "when" to LexTokenType.WHEN,
            "while" to LexTokenType.WHILE,
            "for" to LexTokenType.FOR,
            "in" to LexTokenType.IN,
            "continue" to LexTokenType.CONTINUE,
            "break" to LexTokenType.BREAK,
            "this" to LexTokenType.THIS,
            "super" to LexTokenType.SUPER,
            "class" to LexTokenType.CLASS,
            "struct" to LexTokenType.STRUCT,
            "enum" to LexTokenType.ENUM,
            "interface" to LexTokenType.INTERFACE,
            "func" to LexTokenType.FUNC,
            "return" to LexTokenType.RETURN,
            "as" to LexTokenType.AS,
            "public" to LexTokenType.PUBLIC,
            "private" to LexTokenType.PRIVATE,
            "protected" to LexTokenType.PROTECTED,
            "internal" to LexTokenType.INTERNAL,
            "override" to LexTokenType.OVERRIDE,
            "static" to LexTokenType.STATIC,
            "mutating" to LexTokenType.MUTATING,
            "final" to LexTokenType.FINAL,
            "open" to LexTokenType.OPEN,
            "abstract" to LexTokenType.ABSTRACT,
            "import" to LexTokenType.IMPORT,
            "extern" to LexTokenType.EXTERN,
            "export" to LexTokenType.EXPORT,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testPunctuationLexes() {
        listOf(
            "(" to LexTokenType.L_PAREN,
            ")" to LexTokenType.R_PAREN,
            "[" to LexTokenType.L_BRACKET,
            "]" to LexTokenType.R_BRACKET,
            "{" to LexTokenType.L_CURLY,
            "}" to LexTokenType.R_CURLY,
            "<" to LexTokenType.L_ARROW,
            ">" to LexTokenType.R_ARROW,
            "," to LexTokenType.COMMA,
            "." to LexTokenType.PERIOD,
            "::" to LexTokenType.DOUBLE_COLON,
            ":" to LexTokenType.COLON,
            ";" to LexTokenType.SEMICOLON,
            "->" to LexTokenType.ARROW,
            "?" to LexTokenType.QUESTION,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testLiteralLexes() {
        listOf(
            "123" to LexTokenType.INT_LITERAL,
            "0123456789" to LexTokenType.INT_LITERAL,
            "100_000" to LexTokenType.INT_LITERAL,
            "1_2_3_" to LexTokenType.INT_LITERAL,
            "1___2_3_____" to LexTokenType.INT_LITERAL,

            "123.0" to LexTokenType.FLOAT_LITERAL,
            "012345678.1234567890" to LexTokenType.FLOAT_LITERAL,
            "1.00000" to LexTokenType.FLOAT_LITERAL,
            "1_._0_0_000____" to LexTokenType.FLOAT_LITERAL,

            "true" to LexTokenType.TRUE,
            "false" to LexTokenType.FALSE,
            "nil" to LexTokenType.NIL,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testIdentifierLexes() {
        listOf(
            "abc" to LexTokenType.IDENTIFIER,
            "abc1234567890" to LexTokenType.IDENTIFIER,
            "_abc" to LexTokenType.IDENTIFIER,
            "true2" to LexTokenType.IDENTIFIER,
            "falsey" to LexTokenType.IDENTIFIER,
            "_" to LexTokenType.IDENTIFIER,
            "ifelse" to LexTokenType.IDENTIFIER,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testWhitespaceLexes() {
        listOf(
            " " to LexTokenType.WHITESPACE,
            "\t" to LexTokenType.WHITESPACE,
            "\n" to LexTokenType.WHITESPACE,
            "\r" to LexTokenType.WHITESPACE,
            "  \t  \r\n \t " to LexTokenType.WHITESPACE,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testCommentLexes() {
        listOf(
            "//" to LexTokenType.COMMENT,
            "//    */   /*  // " to LexTokenType.COMMENT,
            "// this is a comment" to LexTokenType.COMMENT,
            "// this is a comment" to LexTokenType.COMMENT,
            "// this is a comment" to LexTokenType.COMMENT,

            "/*    */" to LexTokenType.COMMENT,
            "/* /* */ */" to LexTokenType.COMMENT,
            "/* /* /* */ */ */" to LexTokenType.COMMENT,
            "/* some comment */" to LexTokenType.COMMENT,
            "/* some \n multiline \n comment */" to LexTokenType.COMMENT,
            "/* some /* nested */ comment */" to LexTokenType.COMMENT,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    @Test
    fun testOperatorLexes() {
        listOf(
            "+" to LexTokenType.OPERATOR,
            "-" to LexTokenType.OPERATOR,
            "/-+=!*%&|^?~\\" to LexTokenType.OPERATOR,
        ).forEach { textIsLexedWithType(it.first, it.second) }
    }

    private fun textIsLexedWithType(text: String, type: LexTokenType) {
        val ts = when (val result = lex(text)) {
            is PlowResult.Ok -> result.result
            else -> throw AssertionError()
        }
        assertEquals(type, ts.pop().type)
        assertTrue(ts.isExhausted)
    }

    @Test
    fun testErrorLexes() {
        textProducesError<UnterminatedBlockCommentError>("/*")
        textProducesError<UnterminatedBlockCommentError>("/* /* */")

        textProducesError<InvalidCharacterInNumberLiteralError>("12a")
        textProducesError<InvalidCharacterInNumberLiteralError>("71.3Q")

        textProducesError<CharacterDoesNotStartTokenError>("#")
        textProducesError<CharacterDoesNotStartTokenError>("\uD83D\uDE0A") // 😊
    }

    private inline fun <reified T : PlowError> textProducesError(text: String) {
        when (val result = lex(text)) {
            is PlowResult.Ok -> throw AssertionError()
            is PlowResult.Error -> {
                val issues = result.issues
                assertEquals(1, issues.size)
                assertTrue(issues.first() is T)
            }
        }
    }

    @Test
    fun testLexSequence() {
        val code = "export private func greater(a: std::Int, b: std::Int) -> Bool? { return a == b; }"

        val result = lex(code)
        assertTrue(result is PlowResult.Ok)
        assertTrue(result.issues.isEmpty())

        val ts = result.result

        val expectedTokens = listOf(
            LexToken(LexTokenType.EXPORT, "export", SourceFileLocation(1, 1)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 7)),
            LexToken(LexTokenType.PRIVATE, "private", SourceFileLocation(1, 8)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 15)),
            LexToken(LexTokenType.FUNC, "func", SourceFileLocation(1, 16)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 20)),
            LexToken(LexTokenType.IDENTIFIER, "greater", SourceFileLocation(1, 21)),
            LexToken(LexTokenType.L_PAREN, "(", SourceFileLocation(1, 28)),
            LexToken(LexTokenType.IDENTIFIER, "a", SourceFileLocation(1, 29)),
            LexToken(LexTokenType.COLON, ":", SourceFileLocation(1, 30)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 31)),
            LexToken(LexTokenType.IDENTIFIER, "std", SourceFileLocation(1, 32)),
            LexToken(LexTokenType.DOUBLE_COLON, "::", SourceFileLocation(1, 35)),
            LexToken(LexTokenType.IDENTIFIER, "Int", SourceFileLocation(1, 37)),
            LexToken(LexTokenType.COMMA, ",", SourceFileLocation(1, 40)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 41)),
            LexToken(LexTokenType.IDENTIFIER, "b", SourceFileLocation(1, 42)),
            LexToken(LexTokenType.COLON, ":", SourceFileLocation(1, 43)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 44)),
            LexToken(LexTokenType.IDENTIFIER, "std", SourceFileLocation(1, 45)),
            LexToken(LexTokenType.DOUBLE_COLON, "::", SourceFileLocation(1, 48)),
            LexToken(LexTokenType.IDENTIFIER, "Int", SourceFileLocation(1, 50)),
            LexToken(LexTokenType.R_PAREN, ")", SourceFileLocation(1, 53)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 54)),
            LexToken(LexTokenType.ARROW, "->", SourceFileLocation(1, 55)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 57)),
            LexToken(LexTokenType.IDENTIFIER, "Bool", SourceFileLocation(1, 58)),
            LexToken(LexTokenType.QUESTION, "?", SourceFileLocation(1, 62)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 63)),
            LexToken(LexTokenType.L_CURLY, "{", SourceFileLocation(1, 64)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 65)),
            LexToken(LexTokenType.RETURN, "return", SourceFileLocation(1, 66)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 72)),
            LexToken(LexTokenType.IDENTIFIER, "a", SourceFileLocation(1, 73)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 74)),
            LexToken(LexTokenType.OPERATOR, "==", SourceFileLocation(1, 75)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 77)),
            LexToken(LexTokenType.IDENTIFIER, "b", SourceFileLocation(1, 78)),
            LexToken(LexTokenType.SEMICOLON, ";", SourceFileLocation(1, 79)),
            LexToken(LexTokenType.WHITESPACE, " ", SourceFileLocation(1, 80)),
            LexToken(LexTokenType.R_CURLY, "}", SourceFileLocation(1, 81)),
        )

        for (et in expectedTokens) {
            assertEquals(et, ts.pop())
        }
        assertTrue(ts.isExhausted)
    }
}