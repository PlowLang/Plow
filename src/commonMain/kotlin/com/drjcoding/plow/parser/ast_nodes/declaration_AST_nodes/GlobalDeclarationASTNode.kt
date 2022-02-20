package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.project.ast.managers.ASTManagers

interface GlobalDeclarationASTNode {
    fun registerIRGlobal(
        astManagers: ASTManagers,
        irManagers: IRManagers
    )
}
