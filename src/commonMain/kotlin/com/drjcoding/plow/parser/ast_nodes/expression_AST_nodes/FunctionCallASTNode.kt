package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.ir.type.FunctionIRType
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.CannotInvokeNonFunctionTypeError
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedNumberOfArgumentsError
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers
import com.drjcoding.plow.project.ast.managers.Scope

data class FunctionCallASTNode(
    val function: ExpressionASTNode,
    val arguments: List<ExpressionASTNode>,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(
        astManagers: ASTManagers,
        irManagers: IRManagers,
        parentScope: Scope,
        localNameResolver: LocalNameResolver,
        expectedReturnType: IRType
    ): Pair<IRCodeBlock, IRValue> {
        val argsIR = arguments.map {
            it.toCodeBlockWithResult(
                astManagers,
                irManagers,
                parentScope,
                localNameResolver,
                expectedReturnType
            )
        }
        val (functionCB, functionIRValue) = function.toCodeBlockWithResult(
            astManagers,
            irManagers,
            parentScope,
            localNameResolver,
            expectedReturnType
        )

        val functionType = functionIRValue.type
        if (functionType !is FunctionIRType) {
            throw CannotInvokeNonFunctionTypeError(this, functionIRValue.type)
        }

        if (functionType.argumentTypes.size != arguments.size) {
            throw MismatchedNumberOfArgumentsError(this, functionType)
        }

        var myCB = IRCodeBlock()
        val argValues = mutableListOf<IRValue>()
        for ((index, ir) in argsIR.withIndex()) {
            val (argCB, argIR) = ir

            val expectedType = (functionIRValue.type as FunctionIRType).argumentTypes[index]
            val foundType = argIR.type
            if (!foundType.isSubtypeOf(expectedType)) {
                throw MismatchedTypesError(expectedType, foundType, arguments[index])
            }

            myCB += argCB
            argValues.add(argIR)
        }

        myCB += functionCB

        val resultVar = myCB.createNewLocalVariable((functionIRValue.type as FunctionIRType).outputType)
        myCB += IRStatement.FunctionCall(IRAssignable.LocalVariable(resultVar), functionIRValue, argValues)

        return myCB to LocalVariableIRValue(resultVar)
    }
}