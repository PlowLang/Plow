package com.drjcoding.plow.parser.cst_nodes.statement_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

/**
 * A [StatementCSTNode] is a component of a [CodeBlockCSTNode], i.e. anything that can live at the top level of a code
 * block.
 */
abstract class StatementCSTNode : CSTNode()