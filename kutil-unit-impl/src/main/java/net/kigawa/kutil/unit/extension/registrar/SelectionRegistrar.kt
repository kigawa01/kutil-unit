package net.kigawa.kutil.unit.extension.registrar

import net.kigawa.kutil.unit.extension.registeroption.DefaultRegisterOption
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.extention.RegisterOptions
import net.kigawa.kutil.unitapi.util.AnnotationUtil

open class SelectionRegistrar(
  getterComponent: UnitStoreComponent, databaseComponent: UnitDatabaseComponent, container: UnitContainer,
): AbstractRegister(getterComponent, databaseComponent, container) {
  fun selectRegister(unitClass: Class<out Any>): (()->Unit)? {
    if (!AnnotationUtil.hasUnitAnnotation(unitClass)) return null
   return registerTask(
     UnitIdentify(unitClass, AnnotationUtil.getUnitNameByAnnotation(unitClass)),
     RegisterOptions(*DefaultRegisterOption.getOption(unitClass))
    )
  }
}