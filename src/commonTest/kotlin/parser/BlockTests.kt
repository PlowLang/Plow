package parser

import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode
import com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes.VariableAccessCSTNode
import com.drjcoding.plow.parser.parse_functions.parseCodeBlock
import kotlin.test.Test

class BlockTests {
    @Test
    fun blockTests() {
        testParse(::parseCodeBlock) {
            "{ }" makes { CodeBlockCSTNode(t(0), listOf(), t(1)) }

            "{ a }" makes {
                CodeBlockCSTNode(
                    t(0),
                    listOf(SWT(v(1).toStat(), null)),
                    t(2)
                )
            }

            "{ a ; }" makes {
                CodeBlockCSTNode(
                    t(0),
                    listOf(SWT(v(1).toStat(), t(2))),
                    t(3)
                )
            }

            "{ a ; b\n c ; }" makes {
                CodeBlockCSTNode(
                    t(0),
                    listOf(
                        SWT(v(1).toStat(), t(2)),
                        SWT(v(3).toStat(), TokenCSTNode(at(7), listOf())),
                        SWT(VariableAccessCSTNode(QualifiedIdentifierCSTNode(listOf(), TokenCSTNode(at(9), listOf(at(8))))).toStat(), t(5))
                    ),
                    t(6)
                )
            }
        }
    }
}