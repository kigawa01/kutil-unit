package net.kigawa.kutil.unit.component

import net.kigawa.kutil.unit.annotation.getter.LateInit
import net.kigawa.kutil.unit.api.component.*
import net.kigawa.kutil.unit.concurrent.ConcurrentList
import net.kigawa.kutil.unit.exception.UnitException
import net.kigawa.kutil.unit.extension.database.ComponentInfoDatabase
import net.kigawa.kutil.unit.api.extention.UnitReflectionExecutor
import java.lang.reflect.Constructor

@LateInit
class UnitReflectionComponentImpl(
  private val container: UnitContainer,
  private val loggerComponent: UnitLoggerComponent,
  private val database: ComponentInfoDatabase,
): UnitReflectionComponent {
  private val executorClasses = ConcurrentList<Class<out UnitReflectionExecutor>>()
  fun addExecutor(executor: UnitReflectionExecutor) {
    database.registerComponent(executor)
    executorClasses.add(executor.javaClass)
  }
  
  override fun addExecutor(executorClass: Class<out UnitReflectionExecutor>) {
    database.registerComponentClass(executorClass)
    executorClasses.add(executorClass)
  }
  
  override fun removeExecutor(executorClass: Class<out UnitReflectionExecutor>) {
    executorClasses.remove(executorClass)
    database.unregisterComponent(executorClass)
  }
  
  override fun <T> callConstructor(constructor: Constructor<T>, initStack: InitStack): T {
    for (executorClass in executorClasses.reversed()) {
      val executor = loggerComponent.catch(null) {
        container.getUnit(executorClass)
      } ?: continue
      return loggerComponent.catch(null) {
        executor.callConstructor(constructor, initStack)
      } ?: continue
    }
    throw UnitException("could not execute executable", constructor, initStack)
  }
}