package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.type.NamedIRType
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.FileChildASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class ClassDeclarationASTNode(
    override val name: SourceString,
    val methods: List<MethodDeclarationASTNode>,
    val members: List<MemberDeclarationASTNode>,
    override val underlyingCSTNode: CSTNode,
) : ASTNode(), FileChildASTNode, TypeDeclarationASTNode {

    override fun findScopesAndNames(
        parentScope: Scope,
        astManagers: ASTManagers
    ) {
        val myScope = parentScope.childWithName(name)
        astManagers.scopes.insertNewScope(myScope)
        astManagers.types.insert(parentScope, this)
        astManagers.imports.add(myScope, myScope)
    }

    override fun registerIRType(astManagers: ASTManagers, irManagers: IRManagers) {
        val parentScope = astManagers.types.parentScopeForType(this)
        val irType = NamedIRType(parentScope, name)
        irManagers.types.registerASTtoTypeAssociation(this, irType)
    }

}