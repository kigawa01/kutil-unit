package net.kigawa.kutil.unitapi.extention

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.component.UnitInfo
import net.kigawa.kutil.unitapi.options.RegisterOptions

interface UnitInfoDatabase {
  fun register(unitInfo: UnitInfo<out Any>, registerOptions: RegisterOptions): Boolean
  fun unregister(unitInfo: UnitInfo<out Any>)
  fun identifyList(): List<UnitIdentify<out Any>>
  
  /**
   * find info by equal class and name
   */
  fun <T: Any> findByIdentify(identify: UnitIdentify<T>): List<UnitInfo<T>>
}