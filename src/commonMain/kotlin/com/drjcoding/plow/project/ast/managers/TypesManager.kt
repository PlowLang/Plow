package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.TypeDeclarationASTNode

class TypesManager : Iterable<TypeDeclarationASTNode> {
    private val allTypes: MutableSet<TypeDeclarationASTNode> = mutableSetOf()
    private val scopesToTypes: MutableMap<Scope, MutableSet<TypeDeclarationASTNode>> = mutableMapOf()
    private val typesToScope: MutableMap<TypeDeclarationASTNode, Scope> = mutableMapOf()

    fun insert(scope: Scope, type: TypeDeclarationASTNode) {
        if (scopesToTypes[scope] == null) scopesToTypes[scope] = mutableSetOf()

        allTypes.add(type)
        scopesToTypes[scope]!!.add(type)
        typesToScope[type] = scope
    }

    fun parentScopeForType(type: TypeDeclarationASTNode) = typesToScope[type]!!

    fun getTypesInScope(scope: Scope) = scopesToTypes[scope]?.toSet() ?: setOf()

    override fun iterator(): Iterator<TypeDeclarationASTNode> = allTypes.iterator()

}

