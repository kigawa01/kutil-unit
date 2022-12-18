package net.kigawa.kutil.unit.component.executor

import net.kigawa.kutil.unit.component.container.UnitContainer
import net.kigawa.kutil.unit.component.factory.InitStack
import net.kigawa.kutil.unit.component.logger.ContainerLoggerComponent
import net.kigawa.kutil.unit.concurrent.ConcurrentList
import net.kigawa.kutil.unit.exception.UnitException
import net.kigawa.kutil.unit.extension.executor.UnitExecutor
import java.lang.reflect.Executable

class ExecutorComponentImpl(
  private val container: UnitContainer,
  private val loggerComponent: ContainerLoggerComponent,
): ExecutorComponent {
  private val executorClasses = ConcurrentList<Class<out UnitExecutor>>()
  override fun addExecutor(executorClass: Class<out UnitExecutor>) {
    executorClasses.add(executorClass)
  }
  
  override fun removeExecutor(executorClass: Class<out UnitExecutor>) {
    executorClasses.remove(executorClass)
  }
  
  override fun callExecutable(executable: Executable, initStack: InitStack): Any {
    for (executorClass in executorClasses.reversed()) {
      val executor = loggerComponent.catch(null, "") {
        container.getUnit(executorClass)
      } ?: continue
      return loggerComponent.catch(null, "") {
        executor.callExecutable(executable, initStack)
      } ?: continue
    }
    throw UnitException("could not execute executable")
  }
}