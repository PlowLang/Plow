package com.drjcoding.plow.plow_project

import com.drjcoding.plow.ir.types.IRType

/**
 * This object allows easy access to all the [IRType]s that live in a specific namespace. It is used for type resolution.
 */
class GlobalTypesManager(types: List<IRType>) {

    private val namespaceToTypes = run {
        val allNamespaces = types.map { it.location }.toSet()
        val map = allNamespaces.associateWith { mutableListOf<IRType>() }.toMutableMap()
        types.forEach { map[it.location]!!.add(it) }
        map as Map<FullyQualifiedLocation, List<IRType>>
    }

    /**
     * Get all the [IRType]s that live directly in the [namespace] [FullyQualifiedLocation].
     */
    fun getTypeInNamespace(namespace: FullyQualifiedLocation): List<IRType> = namespaceToTypes[namespace] ?: listOf()
}