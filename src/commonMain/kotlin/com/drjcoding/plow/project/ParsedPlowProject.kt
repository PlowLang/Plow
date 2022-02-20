package com.drjcoding.plow.project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.project.ast.ASTPlowProject

typealias ParsedPlowProject = FolderStructure<PlowFileCSTNode>

fun ParsedPlowProject.toAST(): PlowResult<ASTPlowProject> =
    this
        .map { cstNode, fileName -> cstNode.toAst(fileName) }
        .flattenToPlowResult()
        .map { ASTPlowProject(it) }