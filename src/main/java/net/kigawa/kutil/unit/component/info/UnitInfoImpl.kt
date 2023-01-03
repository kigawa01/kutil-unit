package net.kigawa.kutil.unit.component.info

import net.kigawa.kutil.unit.annotation.LateInit
import net.kigawa.kutil.unit.annotation.Unit
import net.kigawa.kutil.unit.component.UnitIdentify
import net.kigawa.kutil.unit.extension.getter.UnitGetter

@LateInit
class UnitInfoImpl<T: Any>(
  identify: UnitIdentify<T>,
  override val getter: UnitGetter,
): UnitInfo<T> {
  override val identify: UnitIdentify<T>
  
  init {
    val name = if (identify.name == null || identify.name == "") {
      val unitAnnotation = identify.unitClass.getAnnotation(Unit::class.java)
      if (unitAnnotation == null || unitAnnotation.name == "") identify.unitClass.name
      else unitAnnotation.name
    } else identify.name
    
    this.identify = UnitIdentify(identify.unitClass, name)
  }
}