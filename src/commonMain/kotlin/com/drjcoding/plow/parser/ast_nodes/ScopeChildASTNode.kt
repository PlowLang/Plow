package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

interface ScopeChildASTNode {
    fun findScopesAndNames(
        parentScope: Scope,
        astManagers: ASTManagers
    )
}