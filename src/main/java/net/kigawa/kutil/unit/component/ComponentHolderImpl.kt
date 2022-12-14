package net.kigawa.kutil.unit.component

import net.kigawa.kutil.unit.api.component.*
import net.kigawa.kutil.unit.api.extention.ComponentDatabase
import net.kigawa.kutil.unit.concurrent.ConcurrentList

abstract class ComponentHolderImpl<T: Any>(
  private val container: UnitContainer,
  private val database: ComponentDatabase,
  private val loggerComponent: UnitLoggerComponent,
): ComponentHolder<T> {
  protected val classes = ConcurrentList<Class<out T>>()
  override fun add(clazz: Class<out T>) {
    database.registerComponentClass(clazz)
    classes.add(clazz)
  }
  
  override fun remove(clazz: Class<out T>) {
    classes.remove(clazz)
    database.unregisterComponent(clazz)
  }
  
  fun last(predicate: (T)->Boolean) {
    classes.last {
      predicate(container.getUnit(it))
    }
  }
  
  fun <R: Any> lastMap(transform: (T)->R?): R? {
    for (clazz in classes.reversed()) {
      val instance = loggerComponent.catch(null) {
        container.getUnit(clazz)
      } ?: continue
      return transform(instance) ?: continue
    }
    return null
  }
  
  fun forEach(action: (T)->Unit) {
    classes.forEach {
      action(container.getUnit(it))
    }
  }
}