# 1.2 数据抽象

## 核心概念

- **数据类型** = 一组值 + 一组对这些值的操作
- **抽象数据类型 (ADT)** = 对使用者隐藏内部表示的数据类型。使用者只看到 API（操作），看不到数据怎么存的
- ADT 的好处：可以随时更换内部实现（比如复数从直角坐标换成极坐标），用例代码不用改

## 原始类型 vs 引用类型

Java 只有 8 个基本类型：`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`

其他一切都是对象（引用类型），包括 `String`、数组、所有自定义类。

核心区别——变量里存的是什么：

```
原始类型：变量直接存值
int x = 5;
┌─────┐
│  5  │  ← x
└─────┘

引用类型：变量存地址，指向对象
Counter c = new Counter("heads");
              ┌──────────────────┐
c → [地址] ──→ │ name: "heads"    │
              │ count: 0         │
              └──────────────────┘
```

## 别名

引用类型赋值复制的是地址，不是对象：

```java
Counter c1 = new Counter("ones");
c1.increment();     // count = 1
Counter c2 = c1;    // c2 和 c1 指向同一个对象
c2.increment();     // count = 2
// c1 看到的也是 2
```

原始类型赋值复制的是值本身，互不影响。

## 对象为什么用引用

对象大小不固定（Counter、Date、Transaction 各不相同），如果变量直接存对象本身：
- 赋值/传参要复制整个对象，代价大
- 数组里元素大小不一，内存没法连续排列

用引用（固定大小的地址）全部解决。代价是别名问题。

## null

- 所有引用类型的默认值是 `null`（底层是地址 0，约定的不合法地址）
- 数字类型默认 `0`，`boolean` 默认 `false`
- 对 `null` 调用方法会崩溃（NullPointerException）

## 数组语法

声明 + 创建 = 三个原子操作：

```java
int[] a = new int[4];
// 1. int[] a    — 声明：a 是 int 数组
// 2. new int[4] — 创建：分配 4 个格子，填默认值 0
// 3. =          — 赋值：把数组地址存到 a 里
```

对象数组需要额外一步——逐个创建对象填入：

```java
Counter[] rolls = new Counter[6];   // 6 个 null
for (int i = 0; i < 6; i++)
    rolls[i] = new Counter(i + "'s"); // 逐个填入真实对象
```

## 静态方法 vs 实例方法

| | 静态方法 | 实例方法 |
|---|---|---|
| 调用方式 | `Math.sqrt(2.0)` （类名） | `heads.increment()` （对象名） |
| 数据 | 不拥有数据，输入→输出 | 绑定在具体对象上，操作该对象的状态 |

## 创建对象的原子操作

```java
Counter c = new Counter("heads");
// 1. Counter c           — 声明变量，类型为 Counter
// 2. new Counter("heads") — 在内存中创建对象，调用构造函数初始化
// 3. =                    — 把对象的地址存到 c 里
```

## 实现 ADT（1.2.3）

一个 Java 类里有三种变量：

- **实例变量**：定义在方法外、类里面，生命周期跟对象走，用 `private` 隐藏
- **参数变量**：方法调用时传入，方法结束就消失
- **局部变量**：方法内部声明，方法结束就消失

```java
public class Counter
{
   private final String name;   // 实例变量
   private int count;           // 实例变量

   public Counter(String id)    // id 是参数变量
   {
      name = id;                // 把临时的参数值存到实例变量里
   }

   public void increment()
   { count++; }                 // 操作的是实例变量
}
```

构造函数的职责：把参数传进来的临时值搬到实例变量里长期保存。没有返回值类型（连 `void` 都没有），因为返回对象引用是 Java 自动做的。

## 数据类型的设计（1.2.5）

### `==` vs `equals()`

- `==` 比较变量里存的东西：原始类型比较值，引用类型比较地址
- `equals()` 是自定义方法，你决定"相等"是什么意思

```java
Date d1 = new Date(3, 14, 2026);
Date d2 = new Date(3, 14, 2026);
d1 == d2      // false — 两个不同对象，地址不同
d1.equals(d2) // true  — 年月日相同（需正确实现 equals）
```

### 不可变性（`final`）

- `final` = 只能赋值一次。Date 的字段创建后不该变，用 `final`；Counter 的 count 要变，不用
- **陷阱**：`final` 对引用类型只锁住地址，不锁住对象内容

```java
private final double[] coords;
// coords 永远指向同一个数组，但数组里的值随便改
```

解决办法：构造函数里做保护性复制（复制内容而非地址）

### 封装

- `private` 强制所有人通过方法操作数据，不能直接碰实例变量
- 目的不是"藏起来不让看"，而是强制走你定义的规则（如 Counter 的 count 只能加 1，不能随便改成 -100）
- Python 靠下划线约定（`_count`），Java 靠编译器强制（`private`）

### 接口继承

接口 = 合同。签了合同的类必须实现指定的方法，编译器检查。

```java
public interface Comparable<T> {
    int compareTo(T that);
}

public class Date implements Comparable<Date> {
    public int compareTo(Date that) { ... }  // 必须实现，否则编译报错
}
```

用途：让排序等通用算法能处理任意类型——只要签了 `Comparable` 合同就行。

Python 对应：实现 `__lt__()` 即可，不需要显式声明（鸭子类型）。区别是 Python 运行时才发现错，Java 编译时就拦住。

### 实现继承（子类）

子类自动拥有父类的所有方法和变量：

```java
class Dog extends Animal { ... }  // Java
class Dog(Animal): ...            # Python
```

Java 里所有类隐式 `extends Object`，所以每个类都有 `toString()`、`equals()`、`hashCode()`。

书里的态度：**避免实现继承，优先用接口 + 组合。** 组合（"有一个"）比继承（"是一个"）更松耦合，更容易替换。
