package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.DuplicateNameInScopeError
import com.drjcoding.plow.source_abstractions.SourceString

class LocalNameResolver {
    private val localNames: MutableList<MutableMap<SourceString, Pair<IRValue, ASTNode>>> = mutableListOf()

    fun newScope() {
        localNames.add(mutableMapOf())
    }

    fun dropScope() {
        localNames.removeLast()
    }

    fun addName(name: SourceString, value: IRValue, astNode: ASTNode) {
        val last = localNames.last()
        if (last.containsKey(name)) throw DuplicateNameInScopeError(last[name]!!.second, astNode)

        last[name] = value to astNode
    }

    fun resolveName(name: SourceString): IRValue? {
        for (scope in localNames.reversed()) {
            val (value, _) = scope[name] ?: continue
            return value
        }
        return null
    }
}