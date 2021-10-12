package com.drjcoding.plow.parser.parse_functions

import com.drjcoding.plow.lexer.LexTokenStream
import com.drjcoding.plow.parser.cst_nodes.PlowFileCSTNode
import com.drjcoding.plow.parser.parse_functions.errors.ExtraContentInFileError
import com.drjcoding.plow.parser.parse_functions.parseDecleration.parseDeclarations

/**
 * Parses a Plow file.
 */
fun parsePlowFile(ts: LexTokenStream): PlowFileCSTNode {
    val declarations = parseDeclarations(ts)
    // We use safePeekNs instead of isExhausted because it is okay for their to be whitespace at the end of the file.
    if (ts.safePeekNS() != null) {
        throw ExtraContentInFileError(ts.popNS().range)
    }
    return PlowFileCSTNode(declarations)
}