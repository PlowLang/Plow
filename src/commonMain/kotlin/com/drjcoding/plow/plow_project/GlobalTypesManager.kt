package com.drjcoding.plow.plow_project

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * This object keeps track of all the [ObjectType]s in a Plow project. It is used in conjunction with
 * [TypeResolutionHierarchy] for type resolution.
 */
class GlobalTypesManager(private val types: List<ObjectType>) {

    /**
     * A map of type locations to all the types in that location.
     */
    private val namespaceToTypes = run {
        val allNamespaces = types.map { it.location }.toSet()
        val map = allNamespaces.associateWith { mutableListOf<ObjectType>() }.toMutableMap()
        types.forEach { map[it.location]!!.add(it) }
        map as Map<FullyQualifiedLocation, List<ObjectType>>
    }

    private fun getTypesInNamespace(namespace: FullyQualifiedLocation) = namespaceToTypes[namespace] ?: listOf()

    /**
     * Resolves the given name in the context of the given [TypeResolutionHierarchy]. If no type can be found then
     * returns a [PlowResult.Error] containing. If multiple types can be found for the given constraints a [PlowResult.Error] is returned with a
     * [MultiplePossibleTypesError].
     */
    fun resolveTypeName(type: QualifiedIdentifierASTNode, trh: TypeResolutionHierarchy): PlowResult<ObjectType> {
        for (typeSet in trh) {
            val allMatchingTypes = typeSet.map {
                val possibleTypeLocation = it + type.fullyQualifiedLocation
                val matchingTypes = getTypesInNamespace(possibleTypeLocation)
                    .filter { possibleType -> possibleType.name == type.name }
                matchingTypes
            }.flatten()

            return when (allMatchingTypes.size) {
                0 -> continue
                1 -> PlowResult.Ok(allMatchingTypes.first())
                // This means that there were multiple type candidates for the same level of precedence. We don't know
                // which to choose, so we must create an error.
                else -> PlowResult.Error(MultiplePossibleTypesError(type, allMatchingTypes))
            }
        }
        return PlowResult.Error(NoPossibleTypesError(type))
    }
}

/**
 * Used when a given type could resolve to multiple different types.
 *
 * @param typeQI The [QualifiedIdentifierASTNode] for the type reference we failed to resolve.
 * @param possibleTypes A list of the [ObjectType]s this type could resolve as.
 */
class MultiplePossibleTypesError(
    typeQI: QualifiedIdentifierASTNode,
    possibleTypes: List<ObjectType>
) : PlowError(
    "multiple possible types for type name",
    PlowIssueInfo(typeQI.underlyingCSTNode.range.toPlowIssueTextRange(), "This type could resolve to multiple types."),
    possibleTypes.map { PlowIssueInfo(null, "Possible resolution: $it") }
)

/**
 * Used when no types are found for a given type name.

 * @param typeQI The [QualifiedIdentifierASTNode] for the type reference we failed to resolve.
 */
class NoPossibleTypesError(
    typeQI: QualifiedIdentifierASTNode,
): PlowError(
    "no possible types for type name",
    PlowIssueInfo(typeQI.underlyingCSTNode.range.toPlowIssueTextRange(), "No types were found for this range.")
)

/**
 * Defines the order in which namespaces will be searched for a matching type name.
 */
class TypeResolutionHierarchy : Iterable<TypeResolutionHierarchy.TypeSet> {
    class TypeSet(internal val types: MutableList<FullyQualifiedLocation>) : Iterable<FullyQualifiedLocation> {
        override fun iterator(): Iterator<FullyQualifiedLocation> = types.iterator()
    }

    private val locations: MutableList<TypeSet> = mutableListOf(TypeSet(mutableListOf()))

    /**
     * Adds a [FullyQualifiedLocation] to the hierarchy at the current priority level.
     */
    fun addNamespaces(namespace: FullyQualifiedLocation) {
        locations.last().types.add(namespace)
    }

    /**
     * Decreases the priority of all namespaces added in the future. (i.e. All future added namespaces will be considered
     * after all previously added namespaces.
     */
    fun decreasePriority() {
        locations.add(TypeSet(mutableListOf()))
    }

    override fun iterator(): Iterator<TypeSet> = locations.iterator()
}