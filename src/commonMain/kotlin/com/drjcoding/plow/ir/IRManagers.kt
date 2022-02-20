package com.drjcoding.plow.ir

import com.drjcoding.plow.ir.globals.IRGlobalsManager
import com.drjcoding.plow.ir.type.IRTypeManager

class IRManagers {
    val types = IRTypeManager()
    val globals = IRGlobalsManager()
}