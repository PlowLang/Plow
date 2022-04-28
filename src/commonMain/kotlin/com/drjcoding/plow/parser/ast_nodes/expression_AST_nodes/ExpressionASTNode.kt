package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRAssignable
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRValue
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.CannotAssignToThisExpression
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

abstract class ExpressionASTNode : ASTNode(), StatementASTNode {
    open val isAssignableExpression: Boolean
        get() = false

    open fun toIRAssignable(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        localNameResolver: LocalNameResolver,
    ): IRAssignable {
        throw CannotAssignToThisExpression(this)
    }

    abstract fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): Pair<IRCodeBlock, IRValue>

    override fun toIRCodeBlock(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): IRCodeBlock {
        return toCodeBlockWithResult(astManagers, irManagers, parentScope, localNameResolver, expectedReturnType).first
    }
}