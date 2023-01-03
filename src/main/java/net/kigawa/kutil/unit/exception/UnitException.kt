package net.kigawa.kutil.unit.exception

import net.kigawa.kutil.unit.component.UnitIdentify
import net.kigawa.kutil.unit.component.info.UnitInfo
import net.kigawa.kutil.unit.util.Util

open class UnitException: RuntimeException {
  constructor(message: String, cause: Throwable?, vararg obj: Any?): super(
    Util.createLogMessage(message, obj.toList()),
    cause
  )
  
  constructor(message: String, vararg obj: Any?): this(message, null, *obj)
  constructor(unitClass: Class<*>? = null, name: String? = null, message: String, cause: Throwable? = null):
          this(message = message, cause = cause, unitClass, name)
  
  constructor(unitClass: Class<*>, name: String?, message: String):
          this(unitClass, name, message, null)
  
  constructor(unitClass: Class<*>?, message: String, cause: Throwable?):
          this(unitClass, null, message, cause)
  
  constructor(unitClass: Class<*>, message: String):
          this(unitClass, null, message, null)
  
  constructor(unitInfo: UnitInfo<*>, message: String, cause: Throwable?):
          this(message, unitInfo.identify, cause)
  
  constructor(message: String, identify: UnitIdentify<*>, cause: Throwable?):
          this(unitClass = identify.unitClass, name = identify.name, message = message, cause = cause)
  
  constructor(unitInfo: UnitInfo<*>, message: String):
          this(unitInfo = unitInfo, message = message, cause = null)
  
  constructor(message: String, cause: Throwable?):
          this(unitClass = null, message = message, cause = cause)
}