package parser

import com.drjcoding.plow.parser.cst_nodes.QINamespaceCSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.*
import com.drjcoding.plow.parser.parse_functions.UnexpectedTokenError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseExpression
import kotlin.test.Test

class AtomicExpressionTests {
    @Test
    fun varAccessTests() {
        testParse(::parseExpression) {
            "a" makes {
                VariableAccessCSTNode(QualifiedIdentifierCSTNode(listOf(), t(0)))
            }

            "a::b::c" makes {
                VariableAccessCSTNode(
                    QualifiedIdentifierCSTNode(
                        listOf(
                            QINamespaceCSTNode(t(0), t(1)),
                            QINamespaceCSTNode(t(2), t(3)),
                        ),
                        t(4)
                    )
                )
            }
        }
    }

    @Test
    fun parenthesizedExpressionTests() {
        testParse(::parseExpression) {
            "(a)" makes { ParenthesizedExpressionCSTNode(t(0), v(1), t(2)) }

            "(a + b)" makes { ParenthesizedExpressionCSTNode(t(0), BinaryOpCSTNode(v(1), t(2), v(3)), t(4)) }

            "(a".failsWith<UnexpectedTokenError>()
        }
    }

    @Test
    fun literalTests() {
        testParse(::parseExpression) {
            "123" makes { IntLiteralCSTNode(t(0)) }

            "123.456" makes { FloatLiteralCSTNode(t(0)) }
        }
    }

    @Test
    fun returnTests() {
        testParse(::parseExpression) {
            "return" makes { ReturnExpressionCSTNode(t(0), null) }

            "return a" makes { ReturnExpressionCSTNode(t(0), v(1)) }

            "return a + b" makes { ReturnExpressionCSTNode(t(0), BinaryOpCSTNode(v(1), t(2), v(3))) }
        }
    }
}