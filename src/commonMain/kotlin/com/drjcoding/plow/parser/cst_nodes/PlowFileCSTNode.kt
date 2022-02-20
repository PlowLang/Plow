package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.parser.ast_nodes.PlowFileASTNode
import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.DeclarationCSTNode
import com.drjcoding.plow.source_abstractions.SourceFileLocation
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A Plow file.
 *
 * @member declarations The list of declarations in this file
 */
data class PlowFileCSTNode(
    val imports: List<ImportCSTNode>,
    val declarations: List<DeclarationCSTNode>
) : CSTNode() {
    override val range = if (declarations.isEmpty()) {
        SourceFileRange(SourceFileLocation(0, 0), SourceFileLocation(0, 0))
    } else {
        declarations.first().range + declarations.last().range
    }

    fun toAst(name: SourceString): PlowResult<PlowFileASTNode> =
        declarations.map { it.toASTAsFileChild() }.flattenToPlowResult().map { declarations ->
            PlowFileASTNode(
                name,
                imports.map { it.toAST() },
                declarations,
                this
            )
        }
}