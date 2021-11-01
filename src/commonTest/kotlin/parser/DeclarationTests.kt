package parser

import com.drjcoding.plow.parser.cst_nodes.ImportCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.*
import com.drjcoding.plow.parser.parse_functions.errors.*
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
import com.drjcoding.plow.parser.parse_functions.parseDecleration.*
import com.drjcoding.plow.parser.parse_functions.parseImport
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

    @Test
    fun classDeclarationTests() {
        testParse(::parseClassDeclaration) {
            "" makes { null }

            "class".failsWith<UnexpectedTokenError>()
            "class Foo".failsWith<UnexpectedTokenError>()
            "class Foo {".failsWith<UnexpectedTokenError>()

            "class Foo { }" makes {
                ClassDeclarationCSTNode(t(0), t(1), t(2), listOf(), t(3))
            }

            "class Foo { let a = b }" makes {
                ClassDeclarationCSTNode(t(0), t(1), t(2), listOf(
                    VariableDeclarationCSTNode(t(3), t(4), null, t(5), v(6))
                ), t(7))
            }
        }
    }

    @Test
    fun enumDeclarationTests() {
        testParse(::parseEnumDeclaration) {
            "" makes { null }

            "enum".failsWith<UnexpectedTokenError>()
            "enum Foo".failsWith<UnexpectedTokenError>()
            "enum Foo {".failsWith<UnexpectedTokenError>()
            "enum Foo { A B }".failsWith<UnexpectedTokenError>()

            "enum Foo { }" makes {
                EnumDeclarationCSTNode(t(0), t(1), t(2), listOf(), listOf(), t(3))
            }

            "enum Foo { let a = b }" makes {
                EnumDeclarationCSTNode(t(0), t(1), t(2), listOf(), listOf(
                    VariableDeclarationCSTNode(t(3), t(4), null, t(5), v(6))
                ), t(7))
            }

            "enum Foo { A, }" makes {
                EnumDeclarationCSTNode(t(0), t(1), t(2), listOf(EnumCaseCSTNode(t(3), t(4))), listOf(), t(5))
            }

            "enum Foo { A, B \n let a = b }" makes {
                EnumDeclarationCSTNode(
                    t(0), t(1), t(2),
                    listOf(EnumCaseCSTNode(t(3), t(4)), EnumCaseCSTNode(t(5), null)),
                    listOf(VariableDeclarationCSTNode(t(6), t(7), null, t(8), v(9))),
                    t(10)
                )
            }
        }
    }

    @Test
    fun importTests() {
        testParse(::parseImport) {
            "" makes { null }

            "import foo" makes { ImportCSTNode(t(0), qi(1)) }

            "import ".failsWith<ExpectedQualifiedIdentifier>()
        }
    }

}