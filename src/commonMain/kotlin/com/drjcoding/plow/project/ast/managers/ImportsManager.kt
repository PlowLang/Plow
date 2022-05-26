package com.drjcoding.plow.project.ast.managers

class ImportsManager {
    private val imports: MutableMap<Scope, MutableSet<Scope>> = mutableMapOf()

    private fun ensureScopeHasSet(scope: Scope) {
        if (imports[scope] == null) imports[scope] = mutableSetOf()
    }

    fun add(scope: Scope, importToAdd: Scope) {
        ensureScopeHasSet(scope)
        imports[scope]!!.add(importToAdd)
    }

    fun addAll(scope: Scope, importsToAdd: Collection<Scope>) {
        ensureScopeHasSet(scope)
        imports[scope]!!.addAll(importsToAdd)
    }

    fun getImportedScopes(scope: Scope) = imports[scope] ?: setOf()
}