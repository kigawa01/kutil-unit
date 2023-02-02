package net.kigawa.kutil.unitapi.component

import net.kigawa.kutil.unitapi.extention.UnitStore
import java.util.concurrent.Future

interface UnitInfo<T: Any> {
  val identify: UnitIdentify<T>
  val getter: UnitStore
  fun initGetter(initStack: InitStack) {
    getter.initGetter(identify, initStack)
  }
  
  fun initOrGet(initStack: InitStack): Future<T> {
    return getter.initOrGet(identify, initStack)
  }
  
  fun get(): T {
    return getter.get(identify)
  }
  
  fun instanceOf(superClass: Class<out Any>): Boolean {
    return identify.instanceOf(superClass)
  }
}