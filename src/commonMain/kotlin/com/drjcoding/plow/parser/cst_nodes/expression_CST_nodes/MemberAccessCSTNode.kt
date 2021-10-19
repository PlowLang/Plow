package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.MemberAccessASTNode
import com.drjcoding.plow.parser.cst_nodes.TokenCSTNode

/**
 * An object member being accessed. (Ex. `foo.bar`)
 *
 * @property accessedObject the object being accessed
 * @property dot the period after the object
 * @property member the name of the member being accessed
 */
data class MemberAccessCSTNode(
    val accessedObject: ExpressionCSTNode,
    val dot: TokenCSTNode,
    val member: TokenCSTNode
): ExpressionCSTNode() {
    override val range = accessedObject.range + member.range

    override fun toAST() = MemberAccessASTNode(
        accessedObject.toAST(),
        member.token.text,
        this
    )
}
