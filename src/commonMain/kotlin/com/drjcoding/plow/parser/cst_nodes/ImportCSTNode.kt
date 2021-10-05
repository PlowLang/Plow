package com.drjcoding.plow.parser.cst_nodes

/**
 * An import. [declarationToImport] is the class/function/variable being imported.
 */
data class ImportCSTNode(val importKw: TokenCSTNode, val declarationToImport: QualifiedIdentifierCSTNode): CSTNode() {
    override val range = importKw.range + declarationToImport.range
}