package parser

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.LexToken
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.lex
import com.drjcoding.plow.parser.cst_nodes.*
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.ExpressionCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.VariableAccessCSTNode
import com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes.ExpressionStatementCSTNode
import com.drjcoding.plow.parser.parse_functions.peekNSTokenCSTNode
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun <T : CSTNode> testParse(parseFunction: (LexTokenStream) -> T?, tests: ParseTestsContext<T>.() -> Unit) {
    ParseTestsContext(parseFunction).tests()
}

class ParseTestsContext<T : CSTNode>(val parseFunction: (LexTokenStream) -> T?) {
    infix fun String.makes(getExpectedResult: SingleParseTestContext.() -> T?) {
        when (val maybeTs = lex(this)) {
            is PlowResult.Error -> throw AssertionError("expected code `$this` to lex for parse test.")
            is PlowResult.Ok -> {
                val ts = maybeTs.result
                val expected = SingleParseTestContext(ts).getExpectedResult()
                val actual = parseFunction(ts)
                assertEquals(expected, actual, "expected code `$this` to yield $expected, instead found $actual.")
            }
        }
    }

    inline fun <reified E : PlowError> String.failsWith() {
        when (val maybeTs = lex(this)) {
            is PlowResult.Error -> throw AssertionError("expected code `$this` to lex for parse test.")
            is PlowResult.Ok -> {
                val ts = maybeTs.result
                try {
                    val actual = parseFunction(ts)
                    throw AssertionError("expected code `$this` to fail with ${E::class}, instead it produced $actual.")
                } catch (e: Exception) {
                    assertTrue(
                        e is E,
                        "expected code `$this` to fail with ${E::class}, instead failed with ${e::class}."
                    )
                }
            }
        }
    }
}

class SingleParseTestContext(private val ts: LexTokenStream) {
    /**
     * Creates a [TokenCSTNode] from the token at the specified position (ignoring skippables).
     */
    fun t(i: Int) = ts.peekNSTokenCSTNode(i)

    /**
     * Gets the [LexToken] from the specified position not ignoring skippables.
     */
    fun at(i: Int) = ts.peek(i)

    /**
     * Creates a [QualifiedIdentifierCSTNode] from the token at the specified position (ignoring whitespace).
     */
    fun qi(i: Int) = QualifiedIdentifierCSTNode(listOf(), t(i))

    /**
     * Creates a [VariableAccessCSTNode] using the [QualifiedIdentifierCSTNode] that would be created by calling [qi].
     */
    fun v(i: Int) = VariableAccessCSTNode(qi(i))

    /**
     * The same as [StatementWithTerminatorCSTNode].
     */
    val SWT = ::StatementWithTerminatorCSTNode

    /**
     * Converts this expression to a statement by wrapping it in an [ExpressionStatementCSTNode].
     */
    fun ExpressionCSTNode.toStat() = ExpressionStatementCSTNode(this)
}