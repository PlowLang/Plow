package com.drjcoding.plow.ir.global_values

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [GlobalFunction] holds the type of a function that lives in the global scope. It does not store the implementation.
 * It merely says that a function with this type exists.
 */
data class GlobalFunction(
    override val name: SourceString,
    override val location: FullyQualifiedLocation,
    override val type: IRType
) : Global()