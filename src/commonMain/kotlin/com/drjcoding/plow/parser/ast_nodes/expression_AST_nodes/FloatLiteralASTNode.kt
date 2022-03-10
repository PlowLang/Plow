package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.issues.PlowUnimplementedFeatureError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.source_abstractions.SourceString

data class FloatLiteralASTNode(
    val literal: SourceString,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> {
        throw PlowUnimplementedFeatureError(underlyingCSTNode)
    }
}