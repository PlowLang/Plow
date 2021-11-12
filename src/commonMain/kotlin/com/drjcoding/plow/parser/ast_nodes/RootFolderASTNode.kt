package com.drjcoding.plow.parser.ast_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.source_abstractions.SourceString

class RootFolderASTNode: FolderASTNode() {

    override lateinit var childNamespaces: List<NamespaceASTNode>

    override val name: SourceString? = null

    override val parentNamespace: NamespaceASTNode? = null

    override fun thisNamespacesType(): IRType? = null
}