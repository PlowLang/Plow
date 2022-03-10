package com.drjcoding.plow.ir.function.code_block

import com.drjcoding.plow.ir.globals.IRGlobal
import com.drjcoding.plow.ir.type.FunctionIRType
import com.drjcoding.plow.ir.type.IRType
import com.drjcoding.plow.ir.type.StandardTypes
import com.drjcoding.plow.ir.type.UnitIRType


class IntLiteralIRValue(val int: Int) : SimpleIRValue {
    override val type = StandardTypes.INT_IR_TYPE
}

object UnitIRValue : SimpleIRValue {
    override val type = UnitIRType
}

class FunctionCallIRValue(
    val function: SimpleIRValue,
    val arguments: List<SimpleIRValue>
) : IRValue {
    override val type = (function.type as FunctionIRType).outputType
}

class LocalVariableIRValue(val localVariable: IRLocalVariable) : SimpleIRValue {
    override val type = localVariable.type
}

class GlobalIRValue(val global: IRGlobal) : SimpleIRValue {
    override val type = global.type
}


interface IRValue {
    val type: IRType
}

interface SimpleIRValue : IRValue