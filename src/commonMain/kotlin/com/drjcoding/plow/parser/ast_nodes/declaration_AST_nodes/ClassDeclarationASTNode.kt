package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.types.ObjectType
import com.drjcoding.plow.parser.ast_nodes.NamespaceASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.source_abstractions.SourceString

class ClassDeclarationASTNode(
    name: SourceString,
    parentNamespace: NamespaceASTNode,
    val memberVariables: List<VariableDeclarationASTNode>,
    memberFunctions: List<FunctionDeclarationASTNode>,
    declarations: List<DeclarationASTNode>,
    override val underlyingCSTNode: CSTNode,
): ObjectDeclarationASTNode(name, parentNamespace, memberFunctions, declarations) {
    override fun thisNamespacesType() = ObjectType(parentNamespace.thisNamespace, name)
}