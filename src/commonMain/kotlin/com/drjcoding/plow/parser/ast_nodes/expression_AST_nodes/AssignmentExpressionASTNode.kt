package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRStatement
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.ir.function.code_block.UnitIRValue
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class AssignmentExpressionASTNode(
    val assignTo: ExpressionASTNode,
    val value: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> {
        val toAssignTo = assignTo.toIRAssignable()
        val (valueCB, irValue) = value.toCodeBlockWithResult(,)

        if (!irValue.type.isSubtypeOf(toAssignTo.type)) {
            throw MismatchedTypesError(toAssignTo.type, irValue.type, value)
        }

        return valueCB + IRCodeBlock(
            IRStatement.Assignment(toAssignTo, irValue)
        ) to UnitIRValue
    }
}