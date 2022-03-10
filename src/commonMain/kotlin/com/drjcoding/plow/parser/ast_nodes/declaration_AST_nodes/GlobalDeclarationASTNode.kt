package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.source_abstractions.SourceString

interface GlobalDeclarationASTNode {
    val name: SourceString
    val underlyingCSTNode: CSTNode

    fun registerIRGlobal(
        astManagers: ASTManagers,
        irManagers: IRManagers
    )
}
