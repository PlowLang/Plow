package com.drjcoding.plow.ir.types

import com.drjcoding.plow.plow_project.FullyQualifiedLocation
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Represents the type of a class, enumeration, or structure. This type is fully defined by the location and name of the
 * object. The actual internal variables are defined elsewhere.
 */
class ObjectType(
    override val location: FullyQualifiedLocation,
    val name: SourceString
): IRType()