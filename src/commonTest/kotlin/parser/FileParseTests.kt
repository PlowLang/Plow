package parser

import com.drjcoding.plow.parser.cst_nodes.ImportCSTNode
import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.VariableDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExtraContentInFileError
import com.drjcoding.plow.parser.parse_functions.parsePlowFile
import com.drjcoding.plow.source_abstractions.toSourceString
import kotlin.test.Test

class FileParseTests {
    @Test
    fun fileTests() {
        val testSS = "test".toSourceString()
        testParse({ ts -> parsePlowFile(ts, testSS, null)}) {
            "" makes { PlowFileCSTNode(testSS, null, listOf(), listOf()) }

            "let a = b " makes {
                PlowFileCSTNode(
                    testSS, null,
                    listOf(),
                    listOf(VariableDeclarationCSTNode(t(0), t(1), null, t(2), v(3)))
                )
            }

            "import a" makes {
                PlowFileCSTNode(
                    testSS, null,
                    listOf(ImportCSTNode(t(0), qi(1))),
                    listOf()
                )
            }

            "import a \n let a = b \n let c = a \n\n " makes {
                PlowFileCSTNode(
                    testSS, null,
                    listOf(ImportCSTNode(t(0), qi(1))),
                    listOf(
                        VariableDeclarationCSTNode(t(2), t(3), null, t(4), v(5)),
                        VariableDeclarationCSTNode(t(6), t(7), null, t(8), v(9)),
                    )
                )
            }

            "a".failsWith<ExtraContentInFileError>()
            "let a = 1 a".failsWith<ExtraContentInFileError>()
        }
    }
}