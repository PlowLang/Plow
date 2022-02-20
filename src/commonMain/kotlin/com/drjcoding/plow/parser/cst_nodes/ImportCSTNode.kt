package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.ImportASTNode

/**
 * An import. [declarationToImport] is the class/function/variable being imported.
 */
data class ImportCSTNode(val importKw: TokenCSTNode, val declarationToImport: QualifiedIdentifierCSTNode): CSTNode() {
    override val range = importKw.range + declarationToImport.range

    fun toAST() = ImportASTNode(declarationToImport.toScope(), this)
}