package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.runCatchingExceptionsAsPlowResult
import com.drjcoding.plow.parser.cst_nodes.CSTNode

data class CodeBlockASTNode(
    val statements: List<StatementASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {

    fun toIRCodeBlock(): PlowResult<IRCodeBlock> =
        runCatchingExceptionsAsPlowResult {
            statements
                .map { it.toIRCodeBlock(,) }
                .fold(IRCodeBlock(), IRCodeBlock::plus)
        }

}