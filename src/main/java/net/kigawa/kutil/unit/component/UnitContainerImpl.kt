package net.kigawa.kutil.unit.component

import net.kigawa.kutil.unit.annotation.getter.LateInit
import net.kigawa.kutil.unit.api.component.*

@LateInit
class UnitContainerImpl(
  private val parent: UnitContainer? = null,
): UnitContainer {
  lateinit var closerComponent: UnitCloserComponent
  lateinit var loggerComponent: UnitLoggerComponent
  lateinit var databaseComponent: UnitDatabaseComponent
  
  init {
    ContainerInitializer(this)
  }
  
  override fun removeUnit(identify: UnitIdentify<out Any>) {
    databaseComponent.findByIdentify(identify).forEach {info->
      loggerComponent.catch(null) {
        removeInfo(info)
      }
    }
  }
  
  private fun removeInfo(info: UnitInfo<out Any>) {
    databaseComponent.unregisterInfo(info)
    closerComponent.closeUnit(info)
  }
  
  override fun <T: Any> getUnitList(identify: UnitIdentify<T>): List<T> {
    val list = mutableListOf<T>()
    databaseComponent.findByIdentify(identify).forEach {
      loggerComponent.catch(null) {
        list.add(it.get())
      }
    }
    parent?.let {list.addAll(it.getUnitList(identify))}
    return list
  }
  
  override fun close() {
    removeUnit(Any::class.java)
    parent?.close()
  }
}