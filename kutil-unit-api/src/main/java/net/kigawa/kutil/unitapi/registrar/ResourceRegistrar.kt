package net.kigawa.kutil.unitapi.registrar

interface ResourceRegistrar {
  fun register(rootClass: Class<out Any>) {
    register(rootClass.classLoader, rootClass.`package`.name)
  }
  
  fun register(classLoader: ClassLoader, packageName: String)
  fun register(classLoader: ClassLoader, packageName: String, selectionRegistrar: SelectionRegistrar)
}