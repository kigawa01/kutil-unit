package net.kigawa.kutil.unit.extension.closer

import net.kigawa.kutil.unit.component.info.UnitInfo

class AutoCloseAbleCloser: UnitCloser {
  override fun closeUnit(info: UnitInfo<out Any>): Boolean {
    if (!info.instanceOf(AutoCloseable::class.java)) return false
    (info.get() as AutoCloseable).close()
    return true
  }
}