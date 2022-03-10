package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRStatement
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.ir.function.code_block.UnitIRValue
import com.drjcoding.plow.ir.type.StandardTypes
import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class IfExpressionASTNode(
    val condition: ExpressionASTNode,
    val body: CodeBlockASTNode,
    val elseBody: CodeBlockASTNode?,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> {
        val (conditionCB, conditionValue) = condition.toCodeBlockWithResult(astManagers, irManagers)
        if (conditionValue.type != StandardTypes.BOOLEAN_IR_TYPE) {
            throw MismatchedTypesError(StandardTypes.BOOLEAN_IR_TYPE, conditionValue.type, condition)
        }

        // TODO make if an expression
        val bodyCB = body.toIRCodeBlock().unwrapThrowingErrors()
        val elseCB = elseBody?.toIRCodeBlock()?.unwrapThrowingErrors()

        val elseLabel = IRStatement.Label()
        val endLabel = IRStatement.Label()

        var myCB = conditionCB
        myCB += IRStatement.Jump(endLabel, conditionValue, true)
        if (elseCB != null) myCB += IRStatement.Jump(elseLabel, null, false)
        myCB += bodyCB
        if (elseCB != null) {
            myCB += elseLabel
            myCB += elseCB
        }
        myCB += endLabel

        return myCB to UnitIRValue
    }

}
