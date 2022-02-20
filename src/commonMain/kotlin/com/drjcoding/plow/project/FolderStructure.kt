package com.drjcoding.plow.project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.source_abstractions.SourceString

sealed class FolderStructure<T> {
    data class Folder<U>(val name: SourceString, val children: List<FolderStructure<U>>) : FolderStructure<U>() {
        override fun <V> map(mapper: (U, SourceString) -> V): Folder<V> = Folder(name, children.map { it.map(mapper) })
        override fun forEachWithName(names: List<SourceString>, apply: (U, List<SourceString>, SourceString) -> Unit) {
            val newNames = names + name
            children.forEach { it.forEachWithName(newNames, apply) }
        }

        override fun forEachFolderPath(names: List<SourceString>, apply: (List<SourceString>) -> Unit) {
            val path = names + name
            apply(path)
            children.forEach { it.forEachFolderPath(path, apply) }
        }
    }

    data class File<U>(val name: SourceString, val content: U) : FolderStructure<U>() {
        override fun <V> map(mapper: (U, SourceString) -> V): File<V> = File(name, mapper(content, name))
        override fun forEachWithName(names: List<SourceString>, apply: (U, List<SourceString>, SourceString) -> Unit) {
            apply(content, names, name)
        }

        override fun forEachFolderPath(names: List<SourceString>, apply: (List<SourceString>) -> Unit) {}
    }

    //TODO really these should be an iterator
    abstract fun <V> map(mapper: (T, SourceString) -> V): FolderStructure<V>
    protected abstract fun forEachWithName(
        names: List<SourceString>,
        apply: (T, List<SourceString>, SourceString) -> Unit
    )

    protected abstract fun forEachFolderPath(names: List<SourceString>, apply: (List<SourceString>) -> Unit)

    fun forEachWithName(apply: (T, List<SourceString>, SourceString) -> Unit) {
        this.forEachWithName(listOf(), apply)
    }

    fun forEachFolderPath(apply: (List<SourceString>) -> Unit) {
        val path = listOf<SourceString>()
        apply(path)
        this.forEachFolderPath(path, apply)
    }
}

fun <T> FolderStructure<PlowResult<T>>.flattenToPlowResult(): PlowResult<FolderStructure<T>> = when (this) {
    is FolderStructure.File -> this.flattenToPlowResult()
    is FolderStructure.Folder -> this.flattenToPlowResult()
}

private fun <T> FolderStructure.File<PlowResult<T>>.flattenToPlowResult(): PlowResult<FolderStructure.File<T>> =
    when (this.content) {
        is PlowResult.Ok -> PlowResult.Ok(FolderStructure.File(this.name, this.content.result), this.content.issues)
        is PlowResult.Error -> this.content.changeType()
    }

private fun <T> FolderStructure.Folder<PlowResult<T>>.flattenToPlowResult(): PlowResult<FolderStructure.Folder<T>> =
    children.map { it.flattenToPlowResult() }.flattenToPlowResult().map {
        FolderStructure.Folder(name, it)
    }