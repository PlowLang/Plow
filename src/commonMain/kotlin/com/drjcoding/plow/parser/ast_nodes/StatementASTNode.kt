package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.project.ast.managers.ASTManagers

interface StatementASTNode {
    fun toIRCodeBlock(astManagers: ASTManagers, irManagers: IRManagers): IRCodeBlock
}