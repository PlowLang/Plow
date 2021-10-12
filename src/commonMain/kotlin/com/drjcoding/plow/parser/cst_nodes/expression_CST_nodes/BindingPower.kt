@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.lexer.LexToken
import com.drjcoding.plow.source_abstractions.toUnderlyingString
import kotlin.math.min

enum class BindingPower {
    ATOMIC,
    MEMBER_ACCESS,
    ARRAY_ACCESS,
    FUNCTION_CALL,
    CAST,
    PREFIX_OP,
    MULTIPLICATION,
    ADDITION,
    COMPARISON,
    TYPECHECK,
    EQUALITY,
    AND,
    OR,
    ASSIGNMENT;

    /**
     * Returns the tightest binding of this and [other].
     */
    infix fun tightestOf(other: BindingPower) =
        values()[min(this.ordinal, other.ordinal)]

    infix fun looserThan(other: BindingPower) =
        this.ordinal > other.ordinal

    companion object {
        /**
         * Takes a valid binary operator and gets its binding power.
         */
        fun fromOp(op: LexToken): BindingPower =
            when (val opText = op.text.toUnderlyingString()) {
                "*" -> MULTIPLICATION
                "/" -> MULTIPLICATION
                "+" -> ADDITION
                "-" -> ADDITION
                "<" -> COMPARISON
                "<=" -> COMPARISON
                ">" -> COMPARISON
                ">=" -> COMPARISON
                "==" -> EQUALITY
                "!=" -> EQUALITY
                "&&" -> AND
                "||" -> OR
                "is" -> TYPECHECK
                "as" -> CAST
                else -> throw InvalidBinaryOperatorError(opText)
            }

        /**
         * The loosest binding power.
         */
        val LOOSEST = ASSIGNMENT

    }
}
