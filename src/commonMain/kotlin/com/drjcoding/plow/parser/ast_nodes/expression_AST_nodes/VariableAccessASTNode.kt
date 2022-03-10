package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class VariableAccessASTNode(
    val name: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override val isAssignableExpression: Boolean
        get() = true

    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> {
        TODO("Not yet implemented")
    }
}