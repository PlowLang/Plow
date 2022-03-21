package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.UnitIRType
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.IncorrectReturnTypeError
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
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): Pair<IRCodeBlock, IRValue> {
        val returnType: IRType
        val returnCodeBlock = if (expression == null) {
            returnType = UnitIRType
            IRCodeBlock(
                IRStatement.Return(UnitIRValue)
            )
        } else {
            val (valueCB, irValue) = expression.toCodeBlockWithResult(
                astManagers,
                irManagers,
                parentScope,
                localNameResolver,
                expectedReturnType
            )
            returnType = irValue.type
            val myCb = valueCB + IRCodeBlock(
                IRStatement.Return(irValue)
            )
            myCb
        }

        if (!returnType.isSubtypeOf(expectedReturnType)) {
            throw IncorrectReturnTypeError(this, expectedReturnType, returnType)
        }

        return returnCodeBlock to UnitIRValue
    }
}
