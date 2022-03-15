package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.IRFunctionImplementation
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRLocalVariable
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
import com.drjcoding.plow.ir.function.code_block.LocalVariableIRValue
import com.drjcoding.plow.ir.type.FunctionIRType
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.UnitIRType
import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.flattenToPlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.ASTNode
import com.drjcoding.plow.parser.ast_nodes.CodeBlockASTNode
import com.drjcoding.plow.parser.ast_nodes.type_AST_nodes.TypeASTNode
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope
import com.drjcoding.plow.source_abstractions.SourceString

data class BaseFunctionASTNode(
    val name: SourceString,
    val args: List<BaseFunctionArgASTNode>,
    val returnType: TypeASTNode?,
    val body: CodeBlockASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    fun getIRType(astManagers: ASTManagers, irManagers: IRManagers, scope: Scope): PlowResult<IRType> {
        val argTypes = args.map { it.getIRType(astManagers, irManagers, scope) }.flattenToPlowResult()
        val returnType = returnType?.getIRType(astManagers, irManagers, scope) ?: UnitIRType.toPlowResult()

        if (argTypes is PlowResult.Error) return argTypes.changeType()
        if (returnType is PlowResult.Error) return returnType.changeType()

        return FunctionIRType(argTypes.unwrap(), returnType.unwrap()).toPlowResult()
    }

    fun getIRFunction(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope
    ): PlowResult<IRFunctionImplementation> {
        val functionType = getIRType(astManagers, irManagers, parentScope)
        if (functionType is PlowResult.Error) return functionType.changeType()

        val localNameResolver = LocalNameResolver()
        localNameResolver.newScope()

        var myCB = IRCodeBlock()

        val argLocalVariables: MutableList<IRLocalVariable> = mutableListOf()
        args.forEach {
            val localVar = myCB.createNewLocalVariable(it.getIRType(astManagers, irManagers, parentScope).unwrap())
            argLocalVariables.add(localVar)
            localNameResolver.addName(it.name, LocalVariableIRValue(localVar), it)
        }

        val implementationCodeBlock = body.toIRCodeBlock(astManagers, irManagers, parentScope, localNameResolver)
        if (implementationCodeBlock is PlowResult.Error) return implementationCodeBlock.changeType()
        myCB += implementationCodeBlock.unwrap()

        localNameResolver.dropScope()

        return IRFunctionImplementation(
            parentScope,
            name,
            functionType.unwrap(),
            argLocalVariables,
            myCB
        ).toPlowResult()
    }
}

data class BaseFunctionArgASTNode(
    val name: SourceString,
    val type: TypeASTNode,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    fun getIRType(astManagers: ASTManagers, irManagers: IRManagers, scope: Scope): PlowResult<IRType> =
        type.getIRType(astManagers, irManagers, scope)
}