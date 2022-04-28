package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.globals.IRGlobal
import com.drjcoding.plow.ir.ir_conversions.IRFunctionProducer
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

class FunctionDeclarationASTNode(
    private val underlyingFunction: BaseFunctionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), FileChildASTNode, GlobalDeclarationASTNode, IRFunctionProducer {
    override val name: SourceString
        get() = underlyingFunction.name

    override fun findScopesAndNames(parentScope: Scope, astManagers: ASTManagers) {
        astManagers.globals.insert(parentScope, this)
    }

    override fun registerIRGlobal(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.globals.parentScopeForGlobal(this)
        val type = underlyingFunction.getIRType(astManagers, irManagers, parentScope).unwrapThrowingErrors()
        val irGlobal = IRGlobal(parentScope, underlyingFunction.name, type, underlyingFunction.noMangle)
        irManagers.globals.registerASTtoGlobalAssociation(this, irGlobal)
        irManagers.needingIRConversion.registerFunctionNeedingConversion(this)
    }

    override fun registerIRFunction(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.globals.parentScopeForGlobal(this)
        val irGlobal = irManagers.globals.getGlobalForAstNode(this)
        val irFunction = underlyingFunction.getIRFunction(astManagers, irManagers, parentScope).unwrapThrowingErrors()
        irManagers.conversions.addIRFunction(irGlobal, irFunction)
    }

}
