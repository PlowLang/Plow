package com.drjcoding.plow.ir.globals

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.GlobalDeclarationASTNode

class NoIRGlobalForASTGlobalException(
    val ast: GlobalDeclarationASTNode
) : Exception("No IR global found for ast global $ast.")

class DuplicateGlobalTypesForASTTypeException(
    val ast: GlobalDeclarationASTNode,
    val global1: IRGlobal,
    val global2: IRGlobal
) : Exception("Attempted to associate $global2 with $ast while $global1 was already associated.")

class IRGlobalsManager {
    private val astToIRGlobal: MutableMap<GlobalDeclarationASTNode, IRGlobal> = mutableMapOf()

    fun registerASTtoGlobalAssociation(ast: GlobalDeclarationASTNode, type: IRGlobal) {
        val currentAssociation = astToIRGlobal[ast]
        if (currentAssociation != null) {
            throw DuplicateGlobalTypesForASTTypeException(ast, currentAssociation, type)
        }
        astToIRGlobal[ast] = type
    }

    fun getGlobalForAstNode(type: GlobalDeclarationASTNode): IRGlobal =
        astToIRGlobal[type] ?: throw NoIRGlobalForASTGlobalException(type)

}