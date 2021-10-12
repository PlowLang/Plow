package parser

import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.FunctionDeclarationArgCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.FunctionDeclarationCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.TypeAnnotationCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.VariableDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedCodeBlockError
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeAnnotationError
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeError
import com.drjcoding.plow.parser.parse_functions.errors.UnexpectedTokenError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseFunctionDeclaration
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseVariableDeclaration
import kotlin.test.Test

class DeclarationTests {
    @Test
    fun variableDeclarationTests() {
        testParse(::parseVariableDeclaration) {
            "a" makes { null }

            "let".failsWith<UnexpectedTokenError>()
            "let a".failsWith<UnexpectedTokenError>()
            "let a: ".failsWith<ExpectedTypeError>()
            "let a: Int".failsWith<UnexpectedTokenError>()
            "let a: Int =".failsWith<ExpectedExpressionError>()

            "let a = b" makes { VariableDeclarationCSTNode(t(0), t(1), null, t(2), v(3)) }
            "var a = b" makes { VariableDeclarationCSTNode(t(0), t(1), null, t(2), v(3)) }

            "let a: Int = b" makes {
                VariableDeclarationCSTNode(t(0), t(1), TypeAnnotationCSTNode(t(2), qi(3).asType()), t(4), v(5))
            }
        }
    }

    @Test
    fun functionDeclarationTests() {
        testParse(::parseFunctionDeclaration) {
            "a" makes { null }

            "func".failsWith<UnexpectedTokenError>()
            "func foo".failsWith<UnexpectedTokenError>()
            "func foo(".failsWith<UnexpectedTokenError>()
            "func foo(a".failsWith<ExpectedTypeAnnotationError>()
            "func foo(a:".failsWith<ExpectedTypeError>()
            "func foo(a: Int,".failsWith<UnexpectedTokenError>()
            "func foo(a: Int b".failsWith<UnexpectedTokenError>()
            "func foo()".failsWith<ExpectedCodeBlockError>()
            "func foo() {".failsWith<UnexpectedTokenError>()

            "func foo() {}" makes {
                FunctionDeclarationCSTNode(
                    t(0), t(1), t(2), listOf(), t(3), null, eb(4)
                )
            }

            "func foo(): Int {}" makes {
                FunctionDeclarationCSTNode(
                    t(0), t(1), t(2), listOf(), t(3), TypeAnnotationCSTNode(t(4), qi(5).asType()), eb(6)
                )
            }

            "func foo(a: b) {}" makes {
                FunctionDeclarationCSTNode(
                    t(0), t(1), t(2), listOf(
                        FunctionDeclarationArgCSTNode(t(3), TypeAnnotationCSTNode(t(4), qi(5).asType()), null)
                    ), t(6), null, eb(7)
                )
            }

            "func foo(a: b, c: d,) {}" makes {
                FunctionDeclarationCSTNode(
                    t(0), t(1), t(2), listOf(
                        FunctionDeclarationArgCSTNode(t(3), TypeAnnotationCSTNode(t(4), qi(5).asType()), t(6)),
                        FunctionDeclarationArgCSTNode(t(7), TypeAnnotationCSTNode(t(8), qi(9).asType()), t(10))
                    ), t(11), null, eb(12)
                )
            }
        }
    }
}