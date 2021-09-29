package com.drjcoding.plow.parser.cst_nodes.expression_CST_nodes

import com.drjcoding.plow.parser.cst_nodes.CSTNode

abstract class ExpressionCSTNode: CSTNode() {
    /**
     * How tightly this type of expression binds.
     */
    abstract val bindingPower: BindingPower
}
