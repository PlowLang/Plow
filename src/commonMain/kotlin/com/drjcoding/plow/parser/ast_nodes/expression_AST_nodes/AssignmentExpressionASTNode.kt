package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class AssignmentExpressionASTNode(
    val assignTo: ExpressionASTNode,
    val value: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver
    ): Pair<IRCodeBlock, SimpleIRValue> {
        val toAssignTo = assignTo.toIRAssignable()
        val (valueCB, irValue) = value.toCodeBlockWithResult(astManagers, irManagers, parentScope, localNameResolver)

        if (!irValue.type.isSubtypeOf(toAssignTo.type)) {
            throw MismatchedTypesError(toAssignTo.type, irValue.type, value)
        }

        return valueCB + IRCodeBlock(
            IRStatement.Assignment(toAssignTo, irValue)
        ) to UnitIRValue
    }
}