package net.kigawa.kutil.unitimpl.extension.database

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.annotation.getter.LateInit
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.extention.*
import net.kigawa.kutil.unitapi.options.RegisterOptions
import net.kigawa.kutil.unitapi.options.RegistrarInstanceOption
import net.kigawa.kutil.unitimpl.concurrent.ConcurrentList
import net.kigawa.kutil.unitimpl.extension.store.InstanceStore

@LateInit
class ComponentDatabaseImpl: ComponentDatabase {
  override lateinit var getterComponent: UnitStoreComponent
  private val infoList = ConcurrentList<UnitInfo<out Any>>()
  
  init {
    registerComponent(this, null)
  }
  
  override fun registerComponent(item: Any, name: String?) {
    val instanceGetter = InstanceStore()
    instanceGetter.register(UnitIdentify(item.javaClass, name), RegisterOptions(RegistrarInstanceOption(item)))
    registerComponent(item.javaClass, instanceGetter)
  }
  
  override fun registerComponent(identify: UnitIdentify<out Any>, getter: UnitStore) {
    val unitInfo = net.kigawa.kutil.unitimpl.UnitInfoImpl(identify, getter)
    unitInfo.initGetter(InitStack())
    infoList.add(unitInfo)
  }
  
  override fun unregisterComponent(identify: UnitIdentify<out Any>) {
    infoList.filter {it.identify == identify}.forEach {
      infoList.remove(it)
    }
  }
  
  override fun identifyList(): List<UnitIdentify<out Any>> {
    return infoList.map {
      it.identify
    }
  }
  
  override fun <T: Any> findByIdentify(identify: UnitIdentify<T>): List<UnitInfo<T>> {
    @Suppress("UNCHECKED_CAST")
    return infoList.filter {it.identify.equalsOrSuperClass(identify)} as List<UnitInfo<T>>
  }
}