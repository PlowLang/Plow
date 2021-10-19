package com.drjcoding.plow.parser.cst_nodes.type_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode

/**
 * A [CSTNode] representing a type.
 */
abstract class TypeCSTNode : CSTNode() {
    abstract fun toAST(): TypeASTNode
}