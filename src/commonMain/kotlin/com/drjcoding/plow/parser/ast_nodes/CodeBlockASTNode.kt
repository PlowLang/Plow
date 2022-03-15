package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.runCatchingExceptionsAsPlowResult
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class CodeBlockASTNode(
    val statements: List<StatementASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {

    fun toIRCodeBlock(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): PlowResult<IRCodeBlock> =
        runCatchingExceptionsAsPlowResult {
            localNameResolver.newScope()

            val cb = statements
                .map { it.toIRCodeBlock(astManagers, irManagers, parentScope, localNameResolver, expectedReturnType) }
                .fold(IRCodeBlock(), IRCodeBlock::plus)

            localNameResolver.dropScope()

            cb
        }

}