package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.GlobalDeclarationASTNode

class GlobalsManager : Iterable<GlobalDeclarationASTNode> {
    private val allGlobals: MutableSet<GlobalDeclarationASTNode> = mutableSetOf()
    private val scopesToGlobals: MutableMap<Scope, MutableSet<GlobalDeclarationASTNode>> = mutableMapOf()
    private val globalsToScope: MutableMap<GlobalDeclarationASTNode, Scope> = mutableMapOf()

    fun insert(scope: Scope, global: GlobalDeclarationASTNode) {
        if (scopesToGlobals[scope] == null) scopesToGlobals[scope] = mutableSetOf()

        allGlobals.add(global)
        scopesToGlobals[scope]!!.add(global)
        globalsToScope[global] = scope
    }

    fun parentScopeForGlobal(global: GlobalDeclarationASTNode) = globalsToScope[global]!!

    private fun simpleGetGlobalsInScope(scope: Scope) = scopesToGlobals[scope]?.toSet() ?: setOf()

    fun getGlobalsInScope(scope: Scope, importsManager: ImportsManager): Set<GlobalDeclarationASTNode> =
        simpleGetGlobalsInScope(scope) + importsManager.getImportedScopes(scope).map { simpleGetGlobalsInScope(it) }.flatten()

    override fun iterator(): Iterator<GlobalDeclarationASTNode> = allGlobals.iterator()
}