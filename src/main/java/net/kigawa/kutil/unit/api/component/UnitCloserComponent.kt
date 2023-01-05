package net.kigawa.kutil.unit.api.component

import net.kigawa.kutil.unit.api.extention.UnitCloser

interface UnitCloserComponent {
  fun closeUnit(identify: UnitInfo<out Any>)
  fun addCloser(closerClass: Class<out UnitCloser>)
  fun removeCloser(closerClass: Class<out UnitCloser>)
}