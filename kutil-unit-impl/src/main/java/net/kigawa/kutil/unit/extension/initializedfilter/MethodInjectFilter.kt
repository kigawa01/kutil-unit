package net.kigawa.kutil.unit.extension.initializedfilter

import net.kigawa.kutil.unitapi.UnitIdentifies
import net.kigawa.kutil.unitapi.annotation.Inject
import net.kigawa.kutil.unitapi.component.InitStack
import net.kigawa.kutil.unitapi.component.UnitFinderComponent
import net.kigawa.kutil.unitapi.extention.InitializedFilter
import net.kigawa.kutil.unitapi.options.FindOptions
import net.kigawa.kutil.unitapi.util.ReflectionUtil

class MethodInjectFilter(
  private val injectorComponent: UnitFinderComponent,
): InitializedFilter {
  override fun <T: Any> filter(obj: T, stack: InitStack): T {
    ReflectionUtil.getInstanceMethod(obj.javaClass).forEach {
      if (ReflectionUtil.isStatic(it)) return@forEach
      it.getAnnotation(Inject::class.java) ?: return@forEach
      val arg =
        injectorComponent.findUnits(UnitIdentifies.createList(it), stack, FindOptions()).toTypedArray()
      it.isAccessible = true
      it.invoke(obj, *arg)
    }
    return obj
  }
}