package parser

import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.TypeAnnotationCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.VariableDeclarationCSTNode
import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.NamedTypeCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExpectedTypeError
import com.drjcoding.plow.parser.parse_functions.errors.UnexpectedTokenError
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.ExpectedExpressionError
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
                VariableDeclarationCSTNode(t(0), t(1), TypeAnnotationCSTNode(t(2), NamedTypeCSTNode(qi(3))), t(4), v(5))
            }
        }
    }
}