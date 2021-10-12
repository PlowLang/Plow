package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes.DeclarationCSTNode

/**
 * An import. [declarationToImport] is the class/function/variable being imported.
 */
data class ImportCSTNode(val importKw: TokenCSTNode, val declarationToImport: QualifiedIdentifierCSTNode): CSTNode(), DeclarationCSTNode {
    override val range = importKw.range + declarationToImport.range
}