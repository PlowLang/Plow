package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.project.ast.managers.errors.DuplicateScopesError

class ScopeManager {
    private val scopes: MutableSet<Scope> = mutableSetOf()

    fun insertNewScope(scope: Scope) {
        if (scopes.contains(scope)) throw DuplicateScopesError(scope)

        scopes.add(scope)
    }

}