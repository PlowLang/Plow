package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.IRType
import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

class EnumDeclarationASTNode(
    name: SourceString,
    parentNamespace: NamespaceASTNode,
    val cases: List<SourceString>,
    memberFunctions: List<FunctionDeclarationASTNode>,
    declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode
) : ObjectDeclarationASTNode(name, parentNamespace, memberFunctions, declarations) {
    override fun thisNamespacesType() = ObjectType(parentNamespace.thisNamespace, name)
}