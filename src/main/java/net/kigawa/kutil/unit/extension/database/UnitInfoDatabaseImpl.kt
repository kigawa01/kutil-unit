package net.kigawa.kutil.unit.extension.database

import net.kigawa.kutil.unit.component.info.UnitInfo
import net.kigawa.kutil.unit.extension.identify.UnitIdentify

class UnitInfoDatabaseImpl: UnitInfoDatabase {
  private val infoList = mutableListOf<UnitInfo>()
  override fun register(unitInfo: UnitInfo) {
    synchronized(infoList) {
      findByClass(unitInfo.identify)?.let {infoList.remove(it)}
      infoList.add(unitInfo)
    }
  }
  
  override fun identifyList(): MutableList<UnitIdentify> {
    return synchronized(infoList) {
      infoList.map {it.identify}
    }.toMutableList()
  }
  
  override fun findOneEquals(identify: UnitIdentify): MutableList<UnitInfo> {
    return synchronized(infoList) {
      infoList.filter {it.identify.equalsOrSuperClass(identify)}
    }.toMutableList()
  }
  
  override fun findByClass(identify: UnitIdentify): UnitInfo? {
    return infoList.find {it.identify.equalsName(identify)}
  }
}