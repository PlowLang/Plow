package com.drjcoding.plow.lexer

import com.drjcoding.plow.issues.*
import com.drjcoding.plow.source_abstractions.SourceFileLocation
import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * The textual representations of all Plow keywords mapped to their [LexTokenType]s. This also includes `nil`, `true`
 * and `false` even they they are not keywords, because they behave in the same way that they look like identifiers
 * but are not.
 */
private val KEYWORD_TEXT = mapOf(
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
    // Nil, true, and false aren't keywords but behave in the same way in that they look like identifiers but really
    //   have their own microsyntactic class.
    "nil" to LexTokenType.NIL,
    "true" to LexTokenType.TRUE,
    "false" to LexTokenType.FALSE,
)

/**
 * The textual representation of all single character Plow punctuation marks mapped to their [LexTokenType]s.
 */
private val PUNCTUATION_TEXT = mapOf(
    '(' to LexTokenType.L_PAREN,
    ')' to LexTokenType.R_PAREN,
    '[' to LexTokenType.L_BRACKET,
    ']' to LexTokenType.R_BRACKET,
    '{' to LexTokenType.L_CURLY,
    '}' to LexTokenType.R_CURLY,
    '<' to LexTokenType.L_ARROW,
    '>' to LexTokenType.R_ARROW,
    ',' to LexTokenType.COMMA,
    '.' to LexTokenType.PERIOD,
    ':' to LexTokenType.COLON,
    ';' to LexTokenType.SEMICOLON,
    '?' to LexTokenType.QUESTION,
)

/**
 * The textual representation of all two character Plow punctuation marks mapped to their [LexTokenType]s.
 */
private val LONG_PUNCTUATION_TEXT = mapOf(
    "::" to LexTokenType.DOUBLE_COLON,
    "->" to LexTokenType.ARROW,
)

private const val commentStart = "//"
private const val blockCommentStart = "/*"
private const val blockCommentEnd = "*/"

private val Char.isWhitespaceChar: Boolean
    get() = this == ' ' || this == '\t' || this == '\n' || this == '\r'

private val Char.isIdentifierStartChar: Boolean
    get() = this in 'a'..'z' || this in 'A'..'Z' || this == '_'

private val Char.isIdentifierChar: Boolean
    get() = this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9' || this == '_'

private val Char.isOperatorChar: Boolean
    // testing found the use of ||s to be more than an order of magnitude faster than `in listOf()` or `regex`
    get() = this == '/' || this == '.' || this == '-' || this == '+' || this == '=' || this == '!' || this == '*' ||
            this == '<' || this == '%' || this == '>' || this == '&' || this == '|' || this == '^' || this == '?' ||
            this == '~' || this == '\\'

private val Char.isNumberStartChar: Boolean
    get() = this in '0'..'9'

private val Char.isNumberChar: Boolean
    get() = this in '0'..'9' || this == '_'

/**
 * Lexes [code] into a [LexTokenStream]. This function may return the [PlowIssue]s [UnterminatedBlockCommentError],
 * [InvalidCharacterInNumberLiteralError], and [CharacterDoesNotStartTokenError].
 */
fun lex(code: String): PlowResult<LexTokenStream> =
    runCatchingExceptionsAsPlowResult {
        val cs = CharacterStream(code)
        lexCharStream(cs)
    }

private fun lexCharStream(cs: CharacterStream): LexTokenStream {
    val foundTokens: MutableList<LexToken> = mutableListOf()

    while (!cs.isEOF) {
        val token = getNextToken(cs)
        foundTokens.add(token)
    }

    return LexTokenStream(foundTokens)
}

private fun getNextToken(cs: CharacterStream) =
    when {
        // FUTURE these should be ordered based on how common they are
        cs.peek().isWhitespaceChar -> lexWhitespace(cs)
        cs.textIsNext(commentStart) -> lexComment(cs)
        cs.textIsNext(blockCommentStart) -> lexBlockComment(cs)
        cs.peek().isIdentifierStartChar -> lexIdentifier(cs)
        cs.safePeek(2) in LONG_PUNCTUATION_TEXT.keys -> lexLongPunctuation(cs)
        cs.peek() in PUNCTUATION_TEXT.keys -> lexPunctuation(cs)
        cs.peek().isOperatorChar -> lexOperator(cs)
        cs.peek().isNumberStartChar -> lexNumber(cs)
        // TODO strings
        else -> invalidCharacter(cs)
    }

private fun invalidCharacter(cs: CharacterStream): Nothing {
    throw CharacterDoesNotStartTokenError(
        cs.peek(),
        SourceFileRange(cs.sourceFileLocation, 1)
    )
}

private fun lexNumber(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    var text = cs.pop().toString()
    while (cs.safePeek()?.isNumberChar == true) text += cs.pop()

    var type = LexTokenType.INT_LITERAL
    if (cs.safePeek() == '.') {
        type = LexTokenType.FLOAT_LITERAL
        text += cs.pop()
        while (cs.safePeek()?.isNumberChar == true) text += cs.pop()
    }

    if (cs.safePeek()?.isIdentifierStartChar == true) {
        throw InvalidCharacterInNumberLiteralError(
            cs.safePeek()!!,
            SourceFileRange(loc, text.length + 1)
        )
    }

    return LexToken(type, text, loc)
}


private fun lexOperator(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    var text = cs.pop().toString()
    while (cs.safePeek()?.isOperatorChar == true) text += cs.pop()
    return LexToken(LexTokenType.OPERATOR, text, loc)
}

private fun lexLongPunctuation(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    val text = cs.pop(2)
    val type = LONG_PUNCTUATION_TEXT[text]!!
    return LexToken(type, text, loc)
}

private fun lexPunctuation(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    val text = cs.pop()
    val type = PUNCTUATION_TEXT[text]!!
    return LexToken(type, text.toString(), loc)
}

private fun lexBlockComment(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    val startLocations: MutableList<SourceFileLocation> = mutableListOf(loc)
    var text = cs.pop(blockCommentStart.length)
    while (!cs.isEOF && startLocations.isNotEmpty()) {
        when {
            cs.textIsNext(blockCommentStart) -> startLocations.add(cs.sourceFileLocation)
            cs.textIsNext(blockCommentEnd) -> startLocations.removeLast()
        }
        if (startLocations.isNotEmpty()) {
            text += cs.pop()
        }
    }
    if (startLocations.isNotEmpty()) {
        throw UnterminatedBlockCommentError(
            SourceFileRange(startLocations.last(), blockCommentStart.length),
            startLocations.subList(0, startLocations.lastIndex).map { SourceFileRange(it, blockCommentStart.length) }
        )
    } else {
        text += cs.pop(blockCommentEnd.length)
        return LexToken(LexTokenType.COMMENT, text, loc)
    }
}

private fun lexComment(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    var text = cs.pop().toString()
    while (!cs.isEOF && cs.peek() != '\n') text += cs.pop()
    return LexToken(LexTokenType.COMMENT, text, loc)
}

private fun lexIdentifier(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    var text = cs.pop().toString()
    while (cs.safePeek()?.isIdentifierChar == true) text += cs.pop()

    val tokenType = KEYWORD_TEXT[text] ?: LexTokenType.IDENTIFIER
    return LexToken(tokenType, text, loc)
}

private fun lexWhitespace(cs: CharacterStream): LexToken {
    val loc = cs.sourceFileLocation
    var text = cs.pop().toString()
    while (cs.safePeek()?.isWhitespaceChar == true) text += cs.pop()
    return LexToken(LexTokenType.WHITESPACE, text, loc)
}
