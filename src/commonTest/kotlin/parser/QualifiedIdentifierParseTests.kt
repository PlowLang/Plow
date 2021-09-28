package parser

import com.drjcoding.plow.parser.cst_nodes.QINamespaceCSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode
import com.drjcoding.plow.parser.parse_functions.NoIdentifierAfterColonInQIError
import com.drjcoding.plow.parser.parse_functions.parseQualifiedIdentifier
import kotlin.test.Test

class QualifiedIdentifierParseTests {

    @Test
    fun testQualifiedIdentifierValidParses() {
        testParse(::parseQualifiedIdentifier) {

            " foo  :: bar::baz" makes {
                QualifiedIdentifierCSTNode(
                    listOf(QINamespaceCSTNode(t(0), t(1)), QINamespaceCSTNode(t(2), t(3))),
                    t(4)
                )
            }

            "bar" makes {
                QualifiedIdentifierCSTNode(listOf(), t(0))
            }

            "" makes { null }
            "()" makes { null }
            "if" makes { null }
            "::" makes { null }

            "foo::".failsWith<NoIdentifierAfterColonInQIError>()
            "foo::bar::".failsWith<NoIdentifierAfterColonInQIError>()
        }
    }
}