package net.kigawa.kutil.unit.component.getter

import net.kigawa.kutil.unit.component.async.UnitAsyncComponent
import net.kigawa.kutil.unit.component.container.UnitContainer
import net.kigawa.kutil.unit.component.factory.UnitFactoryComponent
import net.kigawa.kutil.unit.component.logger.ContainerLoggerComponent
import net.kigawa.kutil.unit.concurrent.ConcurrentList
import net.kigawa.kutil.unit.exception.UnitException
import net.kigawa.kutil.unit.extension.database.ComponentInfoDatabase
import net.kigawa.kutil.unit.extension.getter.*
import net.kigawa.kutil.unit.component.UnitIdentify
import net.kigawa.kutil.unit.extension.registeroption.RegisterOptions

class UnitGetterComponentImpl(
  private val container: UnitContainer,
  private val loggerComponent: ContainerLoggerComponent,
  factoryComponent: UnitFactoryComponent,
  asyncComponent: UnitAsyncComponent,
  private val database: ComponentInfoDatabase,
): UnitGetterComponent {
  private val getterClasses = ConcurrentList<Class<out UnitGetter>>()
  
  init {
    val initializeGetter = InitializeGetter(factoryComponent, asyncComponent)
    getterClasses.add(SingletonGetter::class.java)
    database.registerComponent(SingletonGetter::class.java, initializeGetter)
    getterClasses.add(InstanceGetter::class.java)
    database.registerComponent(InstanceGetter::class.java, initializeGetter)
    getterClasses.add(initializeGetter.javaClass)
    database.registerComponent(initializeGetter)
  }
  
  override fun addGetter(getterClass: Class<out UnitGetter>) {
    database.registerComponentClass(getterClass)
    getterClasses.add(getterClass)
  }
  
  override fun removeGetter(getterClass: Class<out UnitGetter>) {
    getterClasses.remove(getterClass)
    database.unregisterComponent(getterClass)
  }
  
  override fun findGetter(identify: UnitIdentify<out Any>, options: RegisterOptions): UnitGetter {
    for (getterClass in getterClasses.reversed()) {
      val getter = loggerComponent.catch(null, "") {
        container.getUnit(getterClass)
      } ?: continue
      
      @Suppress("UNCHECKED_CAST")
      if (loggerComponent.catch(false, "") {
          getter.register(identify, options)
        }) return getter
    }
    throw UnitException("getter is not found", identify, options)
  }
}