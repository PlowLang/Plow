package com.drjcoding.plow.project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.runCatchingExceptionsAsPlowResult
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.parse_functions.parsePlowFile

typealias LexedPlowProject = FolderStructure<LexTokenStream>

fun LexedPlowProject.toCST(): PlowResult<ParsedPlowProject> =
    this.map { ts, _ ->
        runCatchingExceptionsAsPlowResult { parsePlowFile(ts) }
    }.flattenToPlowResult()

