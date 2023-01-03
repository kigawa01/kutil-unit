package net.kigawa.kutil.unit.exception

@Suppress("unused")
class NoFoundUnitException: UnitException {
  constructor(message: String, cause: Throwable?, vararg obj: Any?): super(message, cause, *obj)
  constructor(message: String, vararg obj: Any?): super(message, *obj)
}