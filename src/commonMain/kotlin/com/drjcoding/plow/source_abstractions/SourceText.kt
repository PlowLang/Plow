package com.drjcoding.plow.source_abstractions

/**
 * [SourceString] improves performance by using an [Int] for each unit of text in a source file instead of the full
 * [String]. Generally there is a large amount of repetition in source code so reusing the same strings is inefficient.
 *
 * Convert to and from [SourceString] using [String.toSourceString] and [toUnderlyingString].
 */
typealias SourceString = Int


private var idStrings: MutableMap<SourceString, String> = mutableMapOf()
private var stringIds: MutableMap<String, SourceString> = mutableMapOf()
private var nextId: SourceString = 0


/**
 * Clears the cache of [String] ids. This should only be called once no [SourceString]s remain. (Mainly between
 * separate compilations).
 */
fun resetSourceStringIds() {
    nextId = 0
    idStrings = mutableMapOf()
    stringIds = mutableMapOf()
}


/**
 * Converts this [String] into a [SourceString].
 */
fun String.toSourceString(): SourceString =
    if (this in stringIds) {
        stringIds[this]!!
    } else {
        val id = nextId++
        idStrings[id] = this
        stringIds[this] = id
        id
    }


/**
 * Converts this [SourceString] into the raw string it represents.
 */
fun SourceString.toUnderlyingString(): String = idStrings[this] ?: throw SourceStringAccessedAfterRestException()


/**
 * Thrown when [toUnderlyingString] fails to find the id for the [String] meaning that [resetSourceStringIds] was called
 * while [SourceString]s still existed.
 */
class SourceStringAccessedAfterRestException : Exception()