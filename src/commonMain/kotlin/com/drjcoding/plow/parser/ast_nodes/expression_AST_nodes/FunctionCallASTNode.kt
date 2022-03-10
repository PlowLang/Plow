package com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.ir.function.code_block.*
import com.drjcoding.plow.ir.type.FunctionIRType
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.CannotInvokeNonFunctionTypeError
import com.drjcoding.plow.parser.ast_nodes.expression_AST_nodes.errors.MismatchedTypesError
import com.drjcoding.plow.parser.cst_nodes.CSTNode
import com.drjcoding.plow.project.ast.managers.ASTManagers

data class FunctionCallASTNode(
    val function: ExpressionASTNode,
    val arguments: List<ExpressionASTNode>,
    override val underlyingCSTNode: CSTNode
) : ExpressionASTNode() {
    override fun toCodeBlockWithResult(astManagers: ASTManagers, irManagers: IRManagers): Pair<IRCodeBlock, SimpleIRValue> {
        val argsIR = arguments.map { it.toCodeBlockWithResult(astManagers, irManagers) }
        val (functionCB, functionIRValue) = function.toCodeBlockWithResult(astManagers, irManagers)

        if (functionIRValue.type !is FunctionIRType) {
            throw CannotInvokeNonFunctionTypeError(this, functionIRValue.type)
        }

        var myCB = IRCodeBlock()
        val argValues = mutableListOf<SimpleIRValue>()
        for ((index, ir) in argsIR.withIndex()) {
            val (argCB, argIR) = ir

            val expectedType = (functionIRValue.type as FunctionIRType).argumentTypes[index]
            val foundType = argIR.type
            if (expectedType != foundType) {
                throw MismatchedTypesError(expectedType, foundType, arguments[index])
            }

            myCB += argCB
            argValues.add(argIR)
        }

        myCB += functionCB

        val resultVar = myCB.createNewLocalVariable((functionIRValue.type as FunctionIRType).outputType)
        val resultValue = FunctionCallIRValue(functionIRValue, argValues)

        myCB += IRCodeBlock(IRStatement.Assignment(IRAssignable.LocalVariable(resultVar), resultValue))

        return myCB to LocalVariableIRValue(resultVar)
    }
}