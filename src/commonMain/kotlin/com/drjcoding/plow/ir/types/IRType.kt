package com.drjcoding.plow.ir.types

import com.drjcoding.plow.plow_project.FullyQualifiedLocation

/**
 * An [IRType] is used to represent types in the Plow IR.
 */
abstract class IRType {
    abstract val location: FullyQualifiedLocation
}