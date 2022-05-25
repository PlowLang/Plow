package com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.IRFunctionBody
import com.drjcoding.plow.ir.function.IRFunctionImplementation
import com.drjcoding.plow.ir.function.code_block.IRCodeBlock
import com.drjcoding.plow.ir.function.code_block.IRLocalVariable
import com.drjcoding.plow.ir.function.code_block.LocalNameResolver
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
import com.drjcoding.plow.source_abstractions.toUnderlyingString

data class BaseFunctionASTNode(
    val name: SourceString,
    val args: List<BaseFunctionArgASTNode>,
    val returnType: TypeASTNode?,
    val body: BaseFunctionBody,
    override val underlyingCSTNode: CSTNode
) : ASTNode() {
    val noMangle: Boolean = body is BaseFunctionBody.ExternBody || name.toUnderlyingString() == "main" //TODO hacky

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
            val localVar = myCB.createNewLocalVariable(it.getIRType(astManagers, irManagers, parentScope).unwrap(), true)
            argLocalVariables.add(localVar)
            localNameResolver.addName(it.name, localVar, it)
        }

        val returnType = (functionType.unwrap() as FunctionIRType).returnType

        if (body is BaseFunctionBody.ExternBody) {
            return IRFunctionImplementation(
                null,
                body.llvm,
                (functionType.unwrap() as FunctionIRType).returnType,
                argLocalVariables, //TODO sloppy
                IRFunctionBody.ExternBody,
                noMangle
            ).toPlowResult()
        } else if (body is BaseFunctionBody.ExternCodeBody) {
            return IRFunctionImplementation(
                parentScope,
                name,
                (functionType.unwrap() as FunctionIRType).returnType,
                argLocalVariables, //TODO sloppy
                IRFunctionBody.ExternCodeBody(body.llvmCode),
                noMangle,
            ).toPlowResult()
        }

        val body = (body as BaseFunctionBody.BlockBody).body

        val implementationCodeBlock =
            body.toIRCodeBlock(astManagers, irManagers, parentScope, localNameResolver, returnType)
        if (implementationCodeBlock is PlowResult.Error) return implementationCodeBlock.changeType()
        myCB += implementationCodeBlock.unwrap()

        localNameResolver.dropScope()

        return IRFunctionImplementation(
            parentScope,
            name,
            (functionType.unwrap() as FunctionIRType).returnType,
            argLocalVariables,
            IRFunctionBody.BlockBody(myCB),
            noMangle
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

sealed class BaseFunctionBody {
    class BlockBody(val body: CodeBlockASTNode) : BaseFunctionBody()
    class ExternBody(val llvm: SourceString) : BaseFunctionBody()
    class ExternCodeBody(val llvmCode: SourceString) : BaseFunctionBody()
}