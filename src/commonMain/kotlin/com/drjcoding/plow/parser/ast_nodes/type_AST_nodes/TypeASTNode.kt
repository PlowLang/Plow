package com.drjcoding.plow.parser.ast_nodes.type_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

interface TypeASTNode {
    fun getIRType(astManagers: ASTManagers, irManagers: IRManagers, scope: Scope): PlowResult<IRType>
}