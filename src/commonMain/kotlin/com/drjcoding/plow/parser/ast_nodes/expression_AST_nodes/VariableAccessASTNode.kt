package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.GlobalIRValue
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.function.code_block.SimpleIRValue
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class VariableAccessASTNode(
    val name: QualifiedIdentifierASTNode,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override val isAssignableExpression: Boolean
        get() = true

    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver
    ): Pair<IRCodeBlock, SimpleIRValue> {
        if (name.namespaces.isEmpty()) {
            val lookupLocal = localNameResolver.resolveName(name.name)
            if (lookupLocal != null) {
                return IRCodeBlock() to lookupLocal
            }
        }

        val global = astManagers.resolveGlobalName(name, parentScope).unwrapThrowingErrors()
        val irGlobal = irManagers.globals.getGlobalForAstNode(global)
        return IRCodeBlock() to GlobalIRValue(irGlobal)
    }
}