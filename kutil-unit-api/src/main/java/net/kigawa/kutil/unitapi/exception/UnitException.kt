package net.kigawa.kutil.unitapi.exception

import net.kigawa.kutil.unitapi.UnitIdentify

open class UnitException(
  message: String? = null,
  cause: Throwable? = null,
  open val identify: UnitIdentify<out Any>? = null,
) : RuntimeException("$message, $identify", cause)