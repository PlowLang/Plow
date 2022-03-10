package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRAssignable
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.CannotAssignToThisExpression
import com.drjcoding.plow.project.ast.managers.ASTManagers

abstract class ExpressionASTNode : ASTNode(), StatementASTNode {
    open val isAssignableExpression: Boolean
        get() = false

    open fun toIRAssignable(): IRAssignable {
        throw CannotAssignToThisExpression(this)
    }

    abstract fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue>

    override fun toIRCodeBlock(astManagers: ASTManagers, irManagers: IRManagers): IRCodeBlock {
        return toCodeBlockWithResult(astManagers, irManagers).first
    }
}