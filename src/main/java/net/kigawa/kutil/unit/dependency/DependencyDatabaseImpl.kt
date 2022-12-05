package net.kigawa.kutil.unit.dependency

import net.kigawa.kutil.unit.UnitIdentify
import net.kigawa.kutil.unit.UnitInfo

class DependencyDatabaseImpl: DependencyDatabase {
  private val infoList = mutableListOf<UnitInfo>()
  override fun register(unitInfo: UnitInfo) {
    synchronized(infoList) {
      findInfoStrict(unitInfo.identify)?.let {infoList.remove(it)}
      infoList.add(unitInfo)
    }
  }
  
  override fun identifyList(): MutableList<UnitIdentify> {
    return synchronized(infoList) {
      infoList.map {it.identify}
    }.toMutableList()
  }
  
  override fun findInfo(identify: UnitIdentify): MutableList<UnitInfo> {
    return synchronized(infoList) {
      infoList.filter {it.identify.equalsOrSuperClass(identify)}
    }.toMutableList()
  }
  
  override fun findInfoStrict(identify: UnitIdentify): UnitInfo? {
    return infoList.find {it.identify.equalsName(identify)}
  }
}