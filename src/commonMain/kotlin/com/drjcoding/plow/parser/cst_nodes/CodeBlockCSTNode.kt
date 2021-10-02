package com.drjcoding.plow.parser.cst_nodes

/**
 * A [CodeBlockCSTNode] is a series of [StatementWithTerminatorCSTNode]s enclosed between curly brackets.
 */
data class CodeBlockCSTNode(
    val lCurly: TokenCSTNode,
    val statements: List<StatementWithTerminatorCSTNode>,
    val rCurly: TokenCSTNode
): CSTNode() {
    override val range = lCurly.range + rCurly.range
}

/**
 * A [StatementCSTNode] paired optionally with its separator (i.e. a semicolon or newline).
 */
data class StatementWithTerminatorCSTNode(
    val statement: StatementCSTNode,
    val terminator: TokenCSTNode?
): CSTNode() {
    override val range = if (terminator != null) statement.range + terminator.range else statement.range
}