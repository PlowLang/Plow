package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.source_abstractions.SourceString
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class Scope(
    val names: List<SourceString>
) {
    class ScopeIterator(private var currentScope: Scope) : Iterator<Scope> {
        private var done = false

        override fun hasNext(): Boolean = !done

        override fun next(): Scope {
            val thisRound = currentScope
            done = thisRound.names.isEmpty()
            currentScope = currentScope.parent()
            return thisRound
        }
    }

    fun childWithName(name: SourceString) = Scope(names + name)

    fun parent() = Scope(names.dropLast(1))

    fun iterateOverThisAndParents(): ScopeIterator = ScopeIterator(this)

    override fun toString() = names.joinToString(separator = "::") { it.toUnderlyingString() }
}