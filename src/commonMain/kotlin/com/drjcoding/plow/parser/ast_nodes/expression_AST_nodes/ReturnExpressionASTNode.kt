package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class ReturnExpressionASTNode(
    val expression: ExpressionASTNode?,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver
    ): Pair<IRCodeBlock, SimpleIRValue> =
        if (expression == null) {
            IRCodeBlock(
                IRStatement.Return(UnitIRValue)
            ) to UnitIRValue
        } else {
            val (valueCB, irValue) = expression.toCodeBlockWithResult(
                astManagers,
                irManagers,
                parentScope,
                localNameResolver
            )
            val myCb = valueCB + IRCodeBlock(
                IRStatement.Return(irValue)
            )
            myCb to UnitIRValue
        }

}