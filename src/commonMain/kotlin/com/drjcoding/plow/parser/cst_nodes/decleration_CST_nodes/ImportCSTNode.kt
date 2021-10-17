package com.drjcoding.plow.parser.cst_nodes.decleration_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An import. [declarationToImport] is the class/function/variable being imported.
 */
data class ImportCSTNode(val importKw: TokenCSTNode, val declarationToImport: QualifiedIdentifierCSTNode): CSTNode(), DeclarationCSTNode {
    override val range = importKw.range + declarationToImport.range
}