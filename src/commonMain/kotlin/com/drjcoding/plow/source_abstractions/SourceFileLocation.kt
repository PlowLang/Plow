package com.drjcoding.plow.source_abstractions

/**
 * Represents the location within a source file where a piece of text was found.
 *
 * @property line The line number of the piece of text. (One based indexing.)
 * @property col The column number of the piece of text. (One based indexing.)
 */
data class SourceFileLocation(val line: Int, val col: Int) {
    override fun toString() = "line $line, column $col"
}