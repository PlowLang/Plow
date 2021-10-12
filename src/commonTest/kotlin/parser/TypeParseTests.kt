package parser

import com.drjcoding.plow.parser.cst_nodes.type_CST_nodes.NamedTypeCSTNode
import com.drjcoding.plow.parser.parse_functions.expression_parse_functions.parseType
import kotlin.test.Test

class TypeParseTests {
    @Test
    fun typeParseTests() {
        testParse(::parseType) {
            "foo" makes { NamedTypeCSTNode(qi(0)) }
        }
    }
}