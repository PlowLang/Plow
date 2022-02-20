package com.drjcoding.plow.parser.ast_nodes.type_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class NamedTypeASTNode(
    val name: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), TypeASTNode {
    override fun getIRType(astManagers: ASTManagers, irManagers: IRManagers, scope: Scope): PlowResult<IRType> =
        astManagers.resolveTypeName(name, scope).map { irManagers.types.getTypeForAstNode(it) }
}
