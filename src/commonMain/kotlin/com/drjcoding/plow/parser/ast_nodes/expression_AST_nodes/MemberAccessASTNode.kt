package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.issues.PlowUnimplementedFeatureError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class MemberAccessASTNode(
    val value: ExpressionASTNode,
    val member: SourceString,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override val isAssignableExpression: Boolean
        get() = true

    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): Pair<IRCodeBlock, SimpleIRValue> {
        throw PlowUnimplementedFeatureError(underlyingCSTNode)
    }
}