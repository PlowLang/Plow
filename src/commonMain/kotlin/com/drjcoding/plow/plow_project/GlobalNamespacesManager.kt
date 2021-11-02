package com.drjcoding.plow.plow_project

/**
 * Keeps a list of every namespace that is globally accessible.
 */
class GlobalNamespacesManager {

    private val namespaces: MutableList<FullyQualifiedLocation> = mutableListOf()

    /**
     * Add a namespace to the list of namespaces.
     */
    fun addNamespace(namespace: FullyQualifiedLocation) {
        namespaces.add(namespace)
    }

}