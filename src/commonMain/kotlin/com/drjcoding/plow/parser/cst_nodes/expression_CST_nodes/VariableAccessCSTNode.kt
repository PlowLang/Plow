package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.parser.cst_nodes.QualifiedIdentifierCSTNode

/**
 * An expression that represents an access to a global or local function or variable.
 *
 * @property variable the variable or function being accessed.
 */
data class VariableAccessCSTNode(val variable: QualifiedIdentifierCSTNode): ExpressionCSTNode() {
    override val range = variable.range
}