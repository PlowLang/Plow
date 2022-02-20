package com.drjcoding.plow.project

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.lexer.lex

typealias RawPlowProject = FolderStructure<String>

fun RawPlowProject.toLexedProject(): PlowResult<LexedPlowProject> =
    this.map { lex(it) }.flattenToPlowResult()

