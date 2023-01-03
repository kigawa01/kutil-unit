package net.kigawa.kutil.unit.extension.registrar

import net.kigawa.kutil.unit.annotation.LateInit
import net.kigawa.kutil.unit.component.UnitIdentify
import net.kigawa.kutil.unit.component.container.UnitContainer
import net.kigawa.kutil.unit.component.database.UnitDatabaseComponent
import net.kigawa.kutil.unit.component.getter.UnitGetterComponent
import net.kigawa.kutil.unit.extension.registeroption.RegisterOptions

@LateInit
open class ClassRegistrar(
  getterComponent: UnitGetterComponent, databaseComponent: UnitDatabaseComponent,
  container: UnitContainer,
):
  AbstractRegister(getterComponent, databaseComponent, container) {
  fun register(unitClass: Class<out Any>) {
    register(unitClass, null)
  }
  
  fun register(unitClass: Class<out Any>, name: String?) {
    register(unitClass, name, RegisterOptions())
  }
  
  fun register(unitClass: Class<out Any>, name: String?, registerOptions: RegisterOptions) {
    register(UnitIdentify(unitClass, name), registerOptions)
  }
  
  fun register(identify: UnitIdentify<out Any>) {
    register(identify, RegisterOptions())
  }
  
  fun register(identify: UnitIdentify<out Any>, registerOptions: RegisterOptions) {
    registerTask(identify, registerOptions).invoke()
  }
}