package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.DeclarationASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.NamespaceCSTNode
import com.drjcoding.plow.parser.cst_nodes.StatementCSTNode

abstract class DeclarationCSTNode: CSTNode() {
    abstract var parentNamespace: NamespaceCSTNode

    abstract fun toAST(): DeclarationASTNode

    abstract val type: DeclarationType
}

enum class DeclarationType {
    VARIABLE,
    FUNCTION,
    OBJECT,
}
