package com.drjcoding.plow.plow_project

import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [FullyQualifiedLocation] can uniquely identify any location in a Plow project including files, declarations, and
 * nested declarations.
 */
data class FullyQualifiedLocation(
    val namespaces: List<SourceString>
) {

    /**
     * Returns a [FullyQualifiedLocation] for a namespace with the name [name] that is a child of this namespace.
     */
    fun child(name: SourceString) = FullyQualifiedLocation(namespaces.plus(name))

    /**
     * This can be used to combine an absolute [FullyQualifiedLocation] (`this`) with a relative [FullyQualifiedLocation]
     * (other).
     */
    operator fun plus(other: FullyQualifiedLocation) = FullyQualifiedLocation(this.namespaces + other.namespaces)
}