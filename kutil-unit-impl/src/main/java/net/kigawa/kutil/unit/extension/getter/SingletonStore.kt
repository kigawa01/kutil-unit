package net.kigawa.kutil.unit.extension.getter

import net.kigawa.kutil.unit.concurrent.ThreadLock
import net.kigawa.kutil.unitapi.annotation.getter.AlwaysInit
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.exception.UnitException
import net.kigawa.kutil.unitapi.extention.RegisterOptions
import net.kigawa.kutil.unitapi.extention.UnitStore
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

@AlwaysInit
class SingletonStore(
  private val factoryComponent: UnitFactoryComponent,
  private val async: UnitAsyncComponent,
  private val components: UnitConfigComponent,
): UnitStore {
  private var obj: Any? = null
  private var registered = false
  private var initLock: ThreadLock? = null
  
  override fun <T: Any> get(identify: UnitIdentify<T>): T {
    @Suppress("UNCHECKED_CAST")
    return obj as T? ?: throw UnitException("unit is not initialized", identify)
  }
  
  override fun <T: Any> initOrGet(identify: UnitIdentify<T>, initStack: InitStack): Future<T> {
    return async.submit(identify) {
      initGetter(identify, initStack)
      get(identify)
    }
  }
  
  override fun initGetter(identify: UnitIdentify<out Any>, initStack: InitStack) {
    initLock?.block(components.timeoutSec, TimeUnit.SECONDS)
    synchronized(this) {
      if (obj != null) return
      initLock = ThreadLock()
    }
    val obj = factoryComponent.init(identify, initStack)
    synchronized(this) {
      this.obj = obj
      initLock?.signeAll()
      initLock = null
    }
  }
  
  override fun register(identify: UnitIdentify<out Any>, options: RegisterOptions): Boolean {
    if (obj != null) return false
    if (registered) return false
    registered = true
    return true
  }
}