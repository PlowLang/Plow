package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.globals.IRValueGlobal
import com.drjcoding.plow.ir.type.NamedIRType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.ast_nodes.ScopeChildASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class EnumDeclarationASTNode(
    override val name: SourceString,
    val cases: List<EnumCaseASTNode>,
    val methods: List<MethodDeclarationASTNode>,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), FileChildASTNode, TypeDeclarationASTNode {

    override fun findScopesAndNames(parentScope: Scope, astManagers: ASTManagers) {
        val myScope = parentScope.childWithName(name)
        astManagers.scopes.insertNewScope(myScope)
        astManagers.types.insert(parentScope, this)
        astManagers.imports.add(myScope, myScope)

        cases.forEach { it.findScopesAndNames(myScope, astManagers) }
    }

    override fun registerIRType(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.types.parentScopeForType(this)
        val irType = NamedIRType(parentScope, name)
        irManagers.types.registerASTtoTypeAssociation(this, irType)

        irManagers.globals
    }
}

data class EnumCaseASTNode(
    val name: SourceString,
    override val underlyingCSTNode: CSTNode
) : ASTNode(), GlobalDeclarationASTNode, ScopeChildASTNode {
    lateinit var parentEnum: EnumDeclarationASTNode

    override fun findScopesAndNames(parentScope: Scope, astManagers: ASTManagers) {
        astManagers.globals.insert(parentScope, this)
    }

    override fun registerIRGlobal(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.globals.parentScopeForGlobal(this)
        val parentEnumType = irManagers.types.getTypeForAstNode(parentEnum)
        val irGlobal = IRValueGlobal(parentScope, name, parentEnumType)
        irManagers.globals.registerASTtoGlobalAssociation(this, irGlobal)
    }
}