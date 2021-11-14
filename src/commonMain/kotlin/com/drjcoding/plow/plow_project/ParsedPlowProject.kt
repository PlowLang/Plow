package com.drjcoding.plow.plow_project

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.RootFolderASTNode

class ParsedPlowProject(
    val rootFolder: RootFolderASTNode
) {
    fun toIR() {
        val globalTypes = collectDeclaredTypes()

    }

    /**
     * Collects all the globally declared types defined in this project into a [GlobalTypesManager].
     */
    private fun collectDeclaredTypes(): GlobalTypesManager  {
        val allTypes: MutableList<ObjectType> = mutableListOf()
        rootFolder.forEach {
            val possibleIRType = it.thisNamespacesType()
            if (possibleIRType != null) allTypes.add(possibleIRType)
        }
        return GlobalTypesManager(allTypes)
    }

}