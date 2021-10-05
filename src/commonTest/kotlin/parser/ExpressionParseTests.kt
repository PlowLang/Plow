package parser

import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.BinaryOpCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.FunctionArgumentCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.FunctionCallCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.MemberAccessCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.UnexpectedTokenError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import kotlin.test.Test

class ExpressionParseTests {

    @Test
    fun generalTests() {
        testParse(::parseExpression) {
            "" makes { null }
        }
    }

    @Test
    fun operatorTests() {
        testParse(::parseExpression) {
            "a + b" makes {
                BinaryOpCSTNode(v(0), t(1), v(2))
            }

            "a + b * c" makes {
                // a + (b * c)
                BinaryOpCSTNode(v(0), t(1), BinaryOpCSTNode(v(2), t(3), v(4)))
            }

            "a * b + c" makes {
                BinaryOpCSTNode(BinaryOpCSTNode(v(0), t(1), v(2)), t(3), v(4))
            }

            "a + ".failsWith<ExpectedExpressionError>()
        }
    }

    @Test
    fun memberAccessTests() {
        testParse(::parseExpression) {
            "a.b" makes {
                MemberAccessCSTNode(v(0), t(1), t(2))
            }

            "a.b + c" makes {
                BinaryOpCSTNode(MemberAccessCSTNode(v(0), t(1), t(2)), t(3), v(4))
            }

            "a + b.c" makes {
                BinaryOpCSTNode(v(0), t(1), MemberAccessCSTNode(v(2), t(3), t(4)))
            }

            "a.".failsWith<UnexpectedTokenError>()
        }
    }

    @Test
    fun functionCallTests() {
        testParse(::parseExpression) {
            "a()" makes {
                FunctionCallCSTNode(v(0), t(1), listOf(), t(2))
            }

            "a(b)" makes {
                FunctionCallCSTNode(v(0), t(1), listOf(FunctionArgumentCSTNode(v(2), null)), t(3))
            }

            "a(b,c,)" makes {
                FunctionCallCSTNode(
                    v(0),
                    t(1),
                    listOf(
                        FunctionArgumentCSTNode(v(2), t(3)),
                        FunctionArgumentCSTNode(v(4), t(5)),
                    ),
                    t(6)
                )
            }

            "a + b()" makes {
                BinaryOpCSTNode(v(0), t(1), FunctionCallCSTNode(v(2), t(3), listOf(), t(4)))
            }

            "a.b()" makes {
                FunctionCallCSTNode(MemberAccessCSTNode(v(0), t(1), t(2)), t(3), listOf(), t(4))
            }

            "a(".failsWith<UnexpectedTokenError>()
            "a(,".failsWith<UnexpectedTokenError>()
            "a(b".failsWith<UnexpectedTokenError>()
            "a(b,".failsWith<UnexpectedTokenError>()
            "a(b c".failsWith<UnexpectedTokenError>()
        }
    }

}