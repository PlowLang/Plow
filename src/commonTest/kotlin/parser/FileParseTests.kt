package parser

import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.VariableDeclarationCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExtraContentInFileError
import com.drjcoding.plow.parser.parse_functions.parsePlowFile
import kotlin.test.Test

class FileParseTests {
    @Test
    fun fileTests() {
        testParse(::parsePlowFile) {
            "" makes { PlowFileCSTNode(listOf()) }

            "let a = b " makes {
                PlowFileCSTNode(
                    listOf(VariableDeclarationCSTNode(t(0), t(1), null, t(2), v(3)))
                )
            }

            "let a = b \n let c = a \n\n " makes {
                PlowFileCSTNode(
                    listOf(
                        VariableDeclarationCSTNode(t(0), t(1), null, t(2), v(3)),
                        VariableDeclarationCSTNode(t(4), t(5), null, t(6), v(7)),
                    )
                )
            }

            "a".failsWith<ExtraContentInFileError>()
            "let a = 1 a".failsWith<ExtraContentInFileError>()
        }
    }
}