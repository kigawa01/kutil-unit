package net.kigawa.kutil.unitapi.extention

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.options.FindOptions

interface UnitFinder {
  fun <T: Any> findUnits(identify: UnitIdentify<T>, findOptions: FindOptions): List<T>?
}