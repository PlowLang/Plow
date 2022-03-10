package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IntLiteralIRValue
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.InvalidIntLiteralError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class IntLiteralASTNode(
    val literal: SourceString,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver
    ): Pair<IRCodeBlock, SimpleIRValue> {
        val int = literal.toUnderlyingString().toIntOrNull() ?: throw InvalidIntLiteralError(this)
        return IRCodeBlock() to IntLiteralIRValue(int)
    }

}