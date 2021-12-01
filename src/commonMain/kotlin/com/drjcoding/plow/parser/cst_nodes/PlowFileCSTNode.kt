package com.drjcoding.plow.parser.cst_nodes

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
    val name: SourceString,
    val parent: FolderCSTNode,
    val imports: List<ImportCSTNode>,
    val declarations: List<DeclarationCSTNode>
): CSTNode(), NamespaceCSTNode {
    override val range = if (declarations.isEmpty()) {
        SourceFileRange(SourceFileLocation(0, 0), SourceFileLocation(0, 0))
    } else {
        declarations.first().range + declarations.last().range
    }

    fun toAST(): PlowFileASTNode {
        val decs = declarations.map { it.toAST() }

        val file = PlowFileASTNode(
            name,
            imports.map { it.toAST() },
            decs,
            this
        )

        decs.forEach { it.parentNamespace = file }

        return file
    }

    override fun toNamespaceASTNode() = toAST()
}