package com.drjcoding.plow.ir

import com.drjcoding.plow.project.ast.ASTPlowProject
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

class IRProject(val astPlowProject: ASTPlowProject) {
    val astManagers = ASTManagers()
    val irManagers = IRManagers()

    private fun findTypesAndGlobals() {
        astPlowProject.astNodes.forEachFolderPath { path ->
            val scope = Scope(path)
            astManagers.scopes.insertNewScope(scope)
        }
        astPlowProject.astNodes.forEachWithName { astNode, folderNames, _ ->
            val scope = Scope(folderNames)
            astNode.findScopesAndNames(scope, astManagers)
        }
    }

    private fun registerIRTypesAndGlobals() {
        astManagers.types.forEach { it.registerIRType(astManagers, irManagers) }
        astManagers.globals.forEach { it.registerIRGlobal(astManagers, irManagers) }
    }

    init {
        findTypesAndGlobals()
        registerIRTypesAndGlobals()
    }
}