package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ImportASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class PlowFileASTNode(
    val name: SourceString,
    val imports: List<ImportASTNode>,
    val children: List<FileChildASTNode>,
    override val underlyingCSTNode: CSTNode,
) : ASTNode(), ScopeChildASTNode {

    override fun findScopesAndNames(
        parentScope: Scope,
        astManagers: ASTManagers
    ) {
        val myScope = parentScope.childWithName(name)
        astManagers.scopes.insertNewScope(myScope)
        astManagers.imports.addAll(myScope, imports.map { it.scope })
        astManagers.imports.add(myScope, myScope) // TODO this should really be ahead of the imports
        children.forEach {
            it.findScopesAndNames(myScope, astManagers)
        }
    }

}

interface FileChildASTNode : ScopeChildASTNode