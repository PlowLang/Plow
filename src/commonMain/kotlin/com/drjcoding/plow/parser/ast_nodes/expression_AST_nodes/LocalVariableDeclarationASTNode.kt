package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseVariableASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class LocalVariableDeclarationASTNode(
    val underlyingVariable: BaseVariableASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), StatementASTNode {
    override fun toIRCodeBlock(astManagers: ASTManagers, irManagers: IRManagers): IRCodeBlock {
        TODO("Not yet implemented")
    }
}