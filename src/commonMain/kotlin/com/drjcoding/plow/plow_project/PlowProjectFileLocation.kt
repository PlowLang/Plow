package com.drjcoding.plow.plow_project

import com.drjcoding.plow.source_abstractions.SourceString

/**
 * A [PlowProjectFileLocation] fully describes the position of any source file, or object derived from a source file, in
 * a Plow project.
 */
data class PlowProjectFileLocation(
    val folderNames: List<SourceString>,
    val fileName: SourceString
)