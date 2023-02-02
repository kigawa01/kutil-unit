package net.kigawa.kutil.unitapi.exception

@Suppress("unused")
class NoSingleUnitException: UnitException {
  constructor(message: String, cause: Throwable?, vararg obj: Any?): super(message, cause, *obj)
  constructor(message: String, vararg obj: Any?): super(message, *obj)
}