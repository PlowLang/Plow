package com.drjcoding.plow.project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult

sealed class FolderStructure<T> {
    data class Folder<U>(val name: String, val children: List<FolderStructure<U>>): FolderStructure<U>() {
        override fun <V> map(mapper: (U) -> V): Folder<V> = Folder(name, children.map { it.map(mapper) })
    }
    data class File<U>(val name: String, val content: U): FolderStructure<U>() {
        override fun <V> map(mapper: (U) -> V): File<V> = File(name, mapper(content))
    }

    abstract fun <V> map(mapper: (T) -> V): FolderStructure<V>
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