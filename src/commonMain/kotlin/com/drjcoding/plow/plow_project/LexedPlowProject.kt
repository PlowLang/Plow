package com.drjcoding.plow.plow_project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flatten
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.ast_nodes.PlowFileASTNode
import com.drjcoding.plow.parser.cst_nodes.FolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.SubfolderCSTNode
import com.drjcoding.plow.parser.parse_functions.parsePlowFile
import com.drjcoding.plow.source_abstractions.SourceString

class LexedPlowProject(
    val files: List<LexedPlowProjectFile>
) {
    fun toParsedPlowProject(): PlowResult<ParsedPlowProject> =
        files
            .map { it.toParsedPlowProjectFile() }
            .flatten()
            .map(::ParsedPlowProject)
}

class LexedPlowProjectFile(
    val name: SourceString,
    val folder: FolderCSTNode,
    val ts: LexTokenStream
) {
    fun toParsedPlowProjectFile(): PlowResult<PlowFileASTNode> =
        parsePlowFile(ts, name, folder).map { it.toAST() }
}