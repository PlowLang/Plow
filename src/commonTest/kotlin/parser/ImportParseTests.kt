package parser

import com.drjcoding.plow.parser.cst_nodes.ImportCSTNode
import com.drjcoding.plow.parser.parse_functions.ExpectedQualifiedIdentifier
import com.drjcoding.plow.parser.parse_functions.parseImport
import kotlin.test.Test

class ImportParseTests {
    @Test
    fun testImportParse() {
        testParse(::parseImport) {
            "" makes { null }

            "import foo" makes { ImportCSTNode(t(0), qi(1)) }

            "import ".failsWith<ExpectedQualifiedIdentifier>()
        }
    }
}