package com.drjcoding.plow.plow_project

import com.drjcoding.plow.issues.PlowIssue
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A simple structure representing a file tree with content of type [T].
 */
data class SimpleFileStructure<T>(
    val children: List<SimpleFileChild<T>>
) {
    /**
     * Converts this [SimpleFileStructure]<T> to a [SimpleFileStructure]<U> by running all content through [mapper].
     */
    fun <U> mapContent(mapper: (T) -> U): SimpleFileStructure<U> =
        SimpleFileStructure(children.map { it.mapContent(mapper) })

    /**
     * Runs [func] on each direct child node of this file structure.
     */
    fun forEachNode(func: (SimpleFileChild<T>) -> Unit) {
        children.forEach { it.forEachNode(func) }
    }

    /**
     * Applies the given function to each content node.
     */
    fun forEach(func: (T) -> Unit) {
        children.forEach { it.forEach(func) }
    }
}

/**
 * If there are any [PlowResult.Error]s in this structure returns a [PlowResult.Error] containing all the issues.
 * Otherwise, returns a [PlowResult.Ok] containing a [SimpleFileStructure] with the content of this [SimpleFileStructure].
 */
fun <T> SimpleFileStructure<PlowResult<T>>.flatten(): PlowResult<SimpleFileStructure<T>> {
    val issues: MutableList<PlowIssue> = mutableListOf()
    this.forEach {
        if (it is PlowResult.Error) {
            issues.addAll(it.issues)
        }
    }
    return when {
        issues.isEmpty() -> PlowResult.Ok(this.mapContent { it.unwrap() })
        else -> PlowResult.Error(issues)
    }
}

/**
 * Anything that can be inside a [SimpleFileStructure].
 */
sealed class SimpleFileChild<T> {
    abstract fun <U> mapContent(mapper: (T) -> U): SimpleFileChild<U>

    fun forEachNode(func: (SimpleFileChild<T>) -> Unit) = func(this)

    abstract fun forEach(func: (T) -> Unit)

    /**
     * A folder containing more [SimpleFileChild]ren.
     *
     * @property name The name of this folder.
     * @property children The [SimpleFileChild]ren contained in this folder.
     */
    data class SimpleFolder<T>(
        val name: SourceString,
        val children: List<SimpleFileChild<T>>
    ) : SimpleFileChild<T>() {
        override fun <U> mapContent(mapper: (T) -> U): SimpleFileChild<U> =
            SimpleFolder(name, children.map { it.mapContent(mapper) })

        override fun forEach(func: (T) -> Unit) {
            children.forEach { it.forEach(func) }
        }
    }

    /**
     * A file in a [SimpleFileStructure].
     *
     * @property content The contents of this file.
     */
    data class SimpleFile<T>(
        val content: T
    ) : SimpleFileChild<T>() {
        override fun <U> mapContent(mapper: (T) -> U): SimpleFileChild<U> =
            SimpleFile(mapper(content))

        override fun forEach(func: (T) -> Unit) {
            func(content)
        }
    }
}

