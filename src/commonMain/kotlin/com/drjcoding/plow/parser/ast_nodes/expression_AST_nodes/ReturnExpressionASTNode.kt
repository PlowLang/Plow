package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRStatement
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.ir.function.code_block.UnitIRValue
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class ReturnExpressionASTNode(
    val expression: ExpressionASTNode?,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> =
        if (expression == null) {
            IRCodeBlock(
                IRStatement.Return(UnitIRValue)
            ) to UnitIRValue
        } else {
            val (valueCB, irValue) = expression.toCodeBlockWithResult(astManagers, irManagers)
            val myCb = valueCB + IRCodeBlock(
                IRStatement.Return(irValue)
            )
            myCb to UnitIRValue
        }

}