package com.drjcoding.plow.ir.ir_conversions

import com.drjcoding.plow.ir.IRManagers
import com.drjcoding.plow.project.ast.managers.ASTManagers

interface IRFunctionProducer {
    fun registerIRFunction(astManagers: ASTManagers, irManagers: IRManagers)
}