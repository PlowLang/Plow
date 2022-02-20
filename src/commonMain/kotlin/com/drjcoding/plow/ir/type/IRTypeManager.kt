package com.drjcoding.plow.ir.type

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.TypeDeclarationASTNode

class NoIRTypeForASTTypeException(
    val ast: TypeDeclarationASTNode
) : Exception("No IR type found for ast type $ast.")

class DuplicateIRTypesForASTTypeException(
    val ast: TypeDeclarationASTNode,
    val ir1: IRType,
    val ir2: IRType
) : Exception("Attempted to associate $ir2 with $ast while $ir1 was already associated.")

class IRTypeManager {
    private val astToIRType: MutableMap<TypeDeclarationASTNode, IRType> = mutableMapOf()

    fun registerASTtoTypeAssociation(ast: TypeDeclarationASTNode, type: IRType) {
        val currentAssociation = astToIRType[ast]
        if (currentAssociation != null) {
            throw DuplicateIRTypesForASTTypeException(ast, currentAssociation, type)
        }
        astToIRType[ast] = type
    }

    fun getTypeForAstNode(type: TypeDeclarationASTNode): IRType =
        astToIRType[type] ?: throw NoIRTypeForASTTypeException(type)

}