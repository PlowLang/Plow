package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

data class QualifiedIdentifierASTNode(
    val namespaces: List<SourceString>,
    val name: SourceString,
    override val underlyingCSTNode: CSTNode
) : ASTNode()
