# kutil-unit

## About

* add unit for java app
* manage unit by container

## Usage

```pom.xml
<dependency>
  <groupId>net.kigawa.kutil</groupId>
  <artifactId>unit</artifactId>
  <version>2.0</version>
</dependency>
```

Unitの登録

register unit

```java
// java

import net.kigawa.kutil.unit.annotation.Unit

@Unit
class Unit
{

}
```

```kotlin
// kotlin
import net.kigawa.kutil.unit.annotation.Unit

@Unit
object Unit {

}
```

Unitをロードして初期化する

load units and init them

```java
import net.kigawa.kutil.unit.classlist.*;
import net.kigawa.kutil.unit.container.*;

import java.util.*;

class Main
{
  public static void main(String[] args)
  {
    var errors = new ArrayList<Throwable>();
    var classList = ClassList.create(getClass());
    var container = UnitContainer.create();
    errors.addAll(container.registerUnits(classList));
    errors.addAll(container.initUnits());

    errors.forEach(Throwable::printStackTrace);
  }
}
```

## Requirement

* java

## Author

* kigawa
    * kigawa.8390@gmail.com

# Making

## Version

### Example: 9.1

* **9.1**
    * **9**: major, destructive
    * **1**: miner

## ToDo
