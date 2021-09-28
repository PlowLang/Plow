package parser

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.lexer.lex
import com.drjcoding.plow.parser.cst_nodes.CSTNode
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
    fun t(i: Int) = ts.peekNSTokenCSTNode(i)
}