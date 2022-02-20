package com.drjcoding.plow.project.ast

import com.drjcoding.plow.parser.ast_nodes.PlowFileASTNode
import com.drjcoding.plow.project.FolderStructure

class ASTPlowProject(
    val astNodes: FolderStructure<PlowFileASTNode>
)