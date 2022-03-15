package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.BaseVariableASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class LocalVariableDeclarationASTNode(
    val underlyingVariable: BaseVariableASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), StatementASTNode {
    override fun toIRCodeBlock(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): IRCodeBlock {
        val type = underlyingVariable.getIRType(astManagers, irManagers, parentScope).unwrapThrowingErrors()
        val (valueCB, valueIR) = underlyingVariable.value.toCodeBlockWithResult(
            astManagers,
            irManagers,
            parentScope,
            localNameResolver,
            expectedReturnType
        )

        if (type != valueIR.type) {
            throw MismatchedTypesError(type, valueIR.type, underlyingVariable.value)
        }

        localNameResolver.addName(underlyingVariable.name, valueIR, this)

        return valueCB
    }
}