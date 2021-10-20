package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

abstract class ASTNode {
    abstract val underlyingCSTNode: CSTNode
}