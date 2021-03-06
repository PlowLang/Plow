package com.drjcoding.plow.lexer

/**
 * Represents the microsyntactic class of a [LexToken].
 */
enum class LexTokenType {
    IDENTIFIER,

    VAR,
    LET,
    IF,
    ELSE,
    WHEN,
    WHILE,
    FOR,
    IN,
    CONTINUE,
    BREAK,
    THIS,
    SUPER,
    CLASS,
    STRUCT,
    ENUM,
    INTERFACE,
    FUNC,
    RETURN,
    IS,
    AS,
    PUBLIC,
    PRIVATE,
    PROTECTED,
    INTERNAL,
    OVERRIDE,
    STATIC,
    MUTATING,
    FINAL,
    OPEN,
    ABSTRACT,
    IMPORT,
    EXTERN,
    EXPORT,

    INT_LITERAL,
    FLOAT_LITERAL,
    STRING_LITERAL,
    TRUE,
    FALSE,
    NIL,

    L_PAREN,
    R_PAREN,
    L_BRACKET,
    R_BRACKET,
    L_CURLY,
    R_CURLY,
    L_ARROW,
    R_ARROW,
    COMMA,
    PERIOD,
    DOUBLE_COLON,
    COLON,
    SEMICOLON,
    ARROW,
    QUESTION,
    ASSIGN,

    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    L_OR_EQUAL,
    G_OR_EQUAL,
    NOT,
    EQUAL,
    NOT_EQUAL,
    AND,
    OR,

    WHITESPACE,
    NEWLINE,
    COMMENT;

    /**
     * True if this type can be safely ignored in between other tokens (ex. whitespace and comments).
     */
    val isSkipable: Boolean
        get() = when (this) {
            WHITESPACE -> true
            NEWLINE -> true
            COMMENT -> true
            else -> false
        }

    /**
     * True if this token type is an operator.
     */
    val isOperator: Boolean
        get() = when (this) {
            PLUS, MINUS, MULTIPLY, DIVIDE, L_ARROW, R_ARROW, L_OR_EQUAL, G_OR_EQUAL, NOT, EQUAL, NOT_EQUAL, AND, OR
                -> true
            else -> false
        }

}
