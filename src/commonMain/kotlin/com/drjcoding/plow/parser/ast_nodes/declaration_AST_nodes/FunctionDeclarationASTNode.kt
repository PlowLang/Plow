package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.globals.IRValueGlobal
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

class FunctionDeclarationASTNode(
    private val underlyingFunction: BaseFunctionASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), FileChildASTNode, GlobalDeclarationASTNode {
    override fun findScopesAndNames(parentScope: Scope, astManagers: ASTManagers) {
        astManagers.globals.insert(parentScope, this)
    }

    override fun registerIRGlobal(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.globals.parentScopeForGlobal(this)
        val functionType = underlyingFunction.getIRType(astManagers, irManagers, parentScope).unwrapThrowingErrors()
        val irGlobal = IRValueGlobal(parentScope, underlyingFunction.name, functionType)
        irManagers.globals.registerASTtoGlobalAssociation(this, irGlobal)
    }

}
