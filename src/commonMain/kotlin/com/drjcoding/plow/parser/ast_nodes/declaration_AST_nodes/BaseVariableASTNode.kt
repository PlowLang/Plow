package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.errors.TypeAnnotationRequiredError
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.ExpressionASTNode
import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

open class BaseVariableASTNode(
    val name: SourceString,
    val type: TypeASTNode?,
    val value: ExpressionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    fun getIRType(astManagers: ASTManagers, irManagers: IRManagers, parentScope: Scope): PlowResult<IRType> =
        type?.getIRType(astManagers, irManagers, parentScope)
            ?: PlowResult.Error(TypeAnnotationRequiredError(underlyingCSTNode))

}