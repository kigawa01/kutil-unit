package net.kigawa.kutil.unit.util

import java.util.*

object Util {
    fun tryCatch(exceptions: LinkedList<Throwable>, func: () -> Unit) {
        try {
            func()
        } catch (e: Throwable) {
            exceptions.add(e)
        }
    }

    fun instanceOf(clazz: Class<*>, superClass: Class<*>): Boolean {
        if (clazz == superClass) return true
        if (clazz.interfaces.contains(superClass)) return true
        for (interfaceClass in clazz.interfaces) {
            if (instanceOf(interfaceClass, superClass)) return true
        }
        if (clazz.superclass == null) return false
        if (clazz.superclass.equals(superClass)) return true
        if (instanceOf(clazz.superclass, superClass)) return true
        return false
    }
}