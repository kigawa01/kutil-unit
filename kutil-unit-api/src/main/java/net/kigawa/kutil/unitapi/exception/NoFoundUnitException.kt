package net.kigawa.kutil.unitapi.exception

import net.kigawa.kutil.unitapi.UnitIdentify

class NoFoundUnitException(
  message: String?,
  cause: Throwable? = null,
  identify: UnitIdentify<out Any>,
) : UnitException(message, cause, identify)