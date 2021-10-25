package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

/**
 * A node in the AST. This is made from the CST. In contrast to the CST the AST does not include syntactical elements,
 * only the semantically important pieces. It is still, however, directly related to the syntax of the language. Very
 * little, if any, processing is done between the CST and the AST, only removal of punctuation.
 */
abstract class ASTNode {
    abstract val underlyingCSTNode: CSTNode
}