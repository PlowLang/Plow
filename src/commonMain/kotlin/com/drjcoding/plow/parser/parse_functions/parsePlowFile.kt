package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.runCatchingExceptionsAsPlowResult
import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.FolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.SubfolderCSTNode
import com.drjcoding.plow.parser.cst_nodes.ImportCSTNode
import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExtraContentInFileError
import com.drjcoding.plow.parser.parse_functions.declaration_parse_functions.parseDeclarations
import com.drjcoding.plow.source_abstractions.SourceString

/**
 * Parses a Plow file.
 */
//TODO maybe this should be a separate function
fun parsePlowFile(ts: LexTokenStream, name: SourceString, parentFolder: FolderCSTNode?): PlowResult<PlowFileCSTNode> {
    return runCatchingExceptionsAsPlowResult {
        val imports = parseImports(ts)
        val declarations = parseDeclarations(ts)
        // We use safePeekNs instead of isExhausted because it is okay for their to be whitespace at the end of the file.
        if (ts.safePeekNS() != null) {
            throw ExtraContentInFileError(ts.popNS().range)
        }
        PlowFileCSTNode(name, parentFolder, imports, declarations)
    }
}

fun parseImports(ts: LexTokenStream): List<ImportCSTNode> {
    val imports: MutableList<ImportCSTNode> = mutableListOf()
    while (true) imports.add(parseImport(ts) ?: break)
    return imports
}