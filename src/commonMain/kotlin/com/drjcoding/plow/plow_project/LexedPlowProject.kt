package com.drjcoding.plow.plow_project

import com.drjcoding.plow.issues.PlowIssue
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.FolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.parser.cst_nodes.RootFolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.SubfolderCSTNode
import com.drjcoding.plow.parser.parse_functions.tryParsingPlowFile
import com.drjcoding.plow.source_abstractions.SourceString

class LexedPlowProject(
    val lexedFiles: SimpleFileStructure<LexedPlowProjectFile>
) {
    fun toParsedPlowProject(): PlowResult<ParsedPlowProject> {
        val root = RootFolderCSTNode()
        val issues: MutableList<PlowIssue> = mutableListOf()

        fun makeMapper(parentFolder: FolderCSTNode): (SimpleFileChild<LexedPlowProjectFile>) -> Unit =
            {
                when (it) {
                    is SimpleFileChild.SimpleFile -> {
                        when (val possibleFileCSTNode = it.content.toCSTNode(parentFolder)) {
                            is PlowResult.Ok -> parentFolder.children.add(possibleFileCSTNode.result)
                            is PlowResult.Error -> issues.addAll(possibleFileCSTNode.issues)
                        }
                    }
                    is SimpleFileChild.SimpleFolder -> {
                        val subfolder = SubfolderCSTNode(it.name, parentFolder)
                        parentFolder.children.add(subfolder)
                        it.forEachNode(makeMapper(subfolder))
                    }
                }
            }

        lexedFiles.forEachNode(makeMapper(root))

        return when {
            issues.isEmpty() -> PlowResult.Ok(ParsedPlowProject(root.toNamespaceASTNode()))
            else -> PlowResult.Error(issues)
        }
    }
}

data class LexedPlowProjectFile(
    val fileName: SourceString,
    val ts: LexTokenStream
) {
    fun toCSTNode(parent: FolderCSTNode): PlowResult<PlowFileCSTNode> =
        tryParsingPlowFile(ts, fileName, parent)

}