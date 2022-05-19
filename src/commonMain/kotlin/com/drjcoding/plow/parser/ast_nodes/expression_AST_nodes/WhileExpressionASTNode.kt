package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.StandardTypes
import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class WhileExpressionASTNode(
    val condition: ExpressionASTNode,
    val body: CodeBlockASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): Pair<IRCodeBlock, IRValue> {
        val (conditionCB, conditionValue) = condition.toCodeBlockWithResult(
            astManagers,
            irManagers,
            parentScope,
            localNameResolver,
            expectedReturnType
        )
        if (!conditionValue.type.isSubtypeOf(StandardTypes.BOOLEAN_IR_TYPE)) {
            throw MismatchedTypesError(StandardTypes.BOOLEAN_IR_TYPE, conditionValue.type, condition)
        }

        val bodyCB = body.toIRCodeBlock(astManagers, irManagers, parentScope, localNameResolver, expectedReturnType)
            .unwrapThrowingErrors()

        val preConditionLabel = IRStatement.Label()
        val endLabel = IRStatement.Label()

        var myCB = IRCodeBlock()
        myCB += preConditionLabel
        myCB += conditionCB
        myCB += IRStatement.Jump(endLabel, conditionValue, true)
        myCB += bodyCB
        myCB += IRStatement.Jump(preConditionLabel, null, false)
        myCB += endLabel
        return myCB to UnitIRValue
    }
}