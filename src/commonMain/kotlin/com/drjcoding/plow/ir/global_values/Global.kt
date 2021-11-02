package com.drjcoding.plow.ir.global_values

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [Global] is anything in a Plow project that can be globally accessed, such as a variable or function.
 */
abstract class Global {
    abstract val name: SourceString
    abstract val location: FullyQualifiedLocation
    abstract val type: IRType
}