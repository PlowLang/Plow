package com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.StatementASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CodeBlockCSTNode
import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * A [StatementCSTNode] is a component of a [CodeBlockCSTNode], i.e. anything that can live at the top level of a code
 * block.
 */
interface StatementCSTNode {
    /**
     * The [SourceFileRange] that this node and all its children, including skipables, inhabit.
     */
    val range: SourceFileRange

    fun toAST(): StatementASTNode
}

