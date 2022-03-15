package com.drjcoding.plow.ir

import com.drjcoding.plow.ir.globals.IRGlobalsManager
import com.drjcoding.plow.ir.ir_conversions.IRConversionsManager
import com.drjcoding.plow.ir.ir_conversions.NeedingIRConversionManager
import com.drjcoding.plow.ir.type.IRTypeManager

class IRManagers {
    val types = IRTypeManager()
    val globals = IRGlobalsManager()
    val needingIRConversion = NeedingIRConversionManager()
    val conversions = IRConversionsManager()
}