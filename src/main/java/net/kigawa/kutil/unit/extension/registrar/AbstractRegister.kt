package net.kigawa.kutil.unit.extension.registrar

import net.kigawa.kutil.unit.component.UnitIdentify
import net.kigawa.kutil.unit.component.container.UnitContainer
import net.kigawa.kutil.unit.component.database.UnitDatabaseComponent
import net.kigawa.kutil.unit.component.factory.InitStack
import net.kigawa.kutil.unit.component.getter.UnitGetterComponent
import net.kigawa.kutil.unit.component.info.UnitInfo
import net.kigawa.kutil.unit.extension.registeroption.RegisterOptions

abstract class AbstractRegister(
  private val getterComponent: UnitGetterComponent,
  private val databaseComponent: UnitDatabaseComponent,
  private val container: UnitContainer,
): UnitRegistrar {
  protected fun registerTask(identify: UnitIdentify<out Any>, registerOptions: RegisterOptions): ()->Unit {
    val getter = getterComponent.findGetter(identify, registerOptions)
    val info = UnitInfo.create(identify, getter)
    
    databaseComponent.findByIdentify(info.identify).forEach {
      container.removeUnit(it.identify)
    }
    
    databaseComponent.registerInfo(info, registerOptions)
    return {info.getter.initGetter(identify, InitStack())}
  }
}