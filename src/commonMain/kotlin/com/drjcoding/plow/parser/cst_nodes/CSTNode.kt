package com.drjcoding.plow.parser.cst_nodes

import com.drjcoding.plow.source_abstractions.SourceFileRange

/**
 * A Node in the CST. This is the type produced by the parser. It is transformed into an AST before most use.
 */
abstract class CSTNode() {

    /**
     * The [SourceFileRange] that this node and all its children, including skipables, inhabit.
     */
    abstract val range: SourceFileRange

}