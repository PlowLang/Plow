package com.drjcoding.plow.lexer

import com.drjcoding.plow.errors.PlowError
import com.drjcoding.plow.errors.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * [UnterminatedBlockCommentError] is used when a block comment is not closed. The [mainInfo] points to the final
 * unmatched opener. The [notes] point to any additional unmatched openers in the same nested block comment.
 *
 * Examples:
 * ```
 * / *
 * ```
 * ```
 * / *   / *    * /
 * ```
 *
 * @param unmatchedOpenerRange The text range for the final opener without a matching closure.
 * @param additionalUnmatchedOpeners The text ranges for any other unmatched openers in the same nested block comment.
 */
class UnterminatedBlockCommentError(
    unmatchedOpenerRange: SourceFileRange,
    additionalUnmatchedOpeners: List<SourceFileRange>,
) : PlowError(
    "unterminated block comment",
    PlowIssueInfo(unmatchedOpenerRange.toPlowIssueTextRange(), "This block comment was not closed."),
    additionalUnmatchedOpeners.map {
        PlowIssueInfo(it.toPlowIssueTextRange(), "This block comment was also unclosed.")
    },
)

/**
 * [InvalidCharacterInNumberLiteralError] is used when an identifier character is appended without separation to the end
 * of a number literal.
 *
 * Examples:
 * ```
 * 34a
 * 71.4Q
 * ```
 *
 * @param char The character that was found at the end of the number literal.
 * @param literalRange The range of the whole number literal including the illegal character.
 */
// FUTURE The lexer should probably eat the rest of token and continue lexing. The full token could be reported with
//  this error.
class InvalidCharacterInNumberLiteralError(
    char: Char,
    literalRange: SourceFileRange
) : PlowError(
    "invalid character in number literal",
    PlowIssueInfo(literalRange.toPlowIssueTextRange(), "The character `$char` is not allowed in number literals.")
)

/**
 * [CharacterDoesNotStartTokenError] is used when the lexer encounters a character that is not the start of any
 * [LexToken].
 *
 * Examples:
 * ```
 * #
 * 😊
 * ```
 *
 * @param char The character encountered.
 * @param charRange The range of the character.
 */
class CharacterDoesNotStartTokenError(
    char: Char,
    charRange: SourceFileRange
) : PlowError(
    "character does not start any token",
    PlowIssueInfo(charRange.toPlowIssueTextRange(), "The character `$char` does not start any valid Plow syntax.")
)