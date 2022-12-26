package net.kigawa.kutil.unit.component.executor

import net.kigawa.kutil.unit.component.factory.InitStack
import net.kigawa.kutil.unit.extension.executor.UnitReflectionExecutor
import java.lang.reflect.Constructor

interface UnitReflectionComponent {
  fun addExecutor(executorClass: Class<out UnitReflectionExecutor>)
  fun removeExecutor(executorClass: Class<out UnitReflectionExecutor>)
  fun <T> callConstructor(constructor: Constructor<T>, initStack: InitStack): T
}