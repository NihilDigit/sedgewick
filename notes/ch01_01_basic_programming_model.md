# 基础编程模型

## 数据类型

- Java 有四种原始数据类型：
  - 整型（`int`，32 位）
  - 双精度浮点（`double`，64 位）
  - 布尔型（`boolean`）
  - 字符型（`char`，16 位）

- 如果不损失信息，数值会被自动提升为更高精度的类型

- 其他常用原始类型：
  - 64 位整数 `long`
  - 16 位整数 `short`
  - 8 位整数 `byte`
  - 32 位单精度浮点 `float`

## 数组

声明、创建、初始化三步：

```Java
double[] a; // 声明
a = new double[N]; // 创建
for (int i = 0; i < N; i++)
  a[i] = 0.0; // 初始化
```

创建时默认值为 0，因此可以简化为一行：

```Java
double[] a = new double[N];
```

也可以直接用字面量声明并初始化：

```Java
int[] a = { 1, 1, 2, 3, 5, 8 };
```

注意：数组变量存储的是对数组对象的引用（地址），而非数组内容本身

```Java
int[] a = new int[N];

int[] b = a; // b 和 a 指向同一个数组对象，并没有复制数据
```

> [!CAUTION]
> 此时对 `b[i]` 的修改会同时作用到 `a[i]` 上，因为它们是同一个对象的两个引用

如果需要真正复制一个数组，应该逐元素复制：

```Java
int[] b = new int[a.length];
for (int i = 0; i < a.length; i++)
  b[i] = a[i];
```

### 多维数组

多维数组就是数组的数组（元素本身也是数组引用）：

```Java
double[][] a = new double[M][N]; // M 行 N 列
```

## 静态方法

Java 的静态方法类似 Python 的模块级函数（顶层 `def`），不依赖对象实例，通过类名直接调用：

```Java
public static double sqrt(double c) {
  if (c < 0) return Double.NaN;
  double err = 1e-15;
  double t = c;
  while (Math.abs(t - c/t) > err * t)
    t = (c/t + t) / 2.0;
  return t;
}
```

| Java                                      | Python 对应                                |
|-------------------------------------------|-------------------------------------------|
| `static` 方法，`ClassName.method()` 调用  | 模块顶层 `def`，`import` 后直接调用        |
| 非 `static` 方法，`obj.method()` 调用     | 类里的 `def`（带 `self`），通过实例调用     |

本书大量使用静态方法，因为讲算法时不需要面向对象，当普通函数用即可。Java 的 `class` 就当 Python 的 `.py` 文件看，`static` 方法当普通函数看。

### `main` 方法

`main` 是 JVM 的固定入口，签名不可更改：

```Java
public static void main(String[] args)
```

- `public`：JVM 从外部调用，必须公开
- `static`：启动时还没创建任何对象，只能是静态方法
- `void`：程序退出码通过 `System.exit(n)` 传，不靠返回值
- `String[] args`：命令行参数（相当于 Python 的 `sys.argv[1:]`，不含程序名）

### 方法的性质

- 方法的参数按值传递：传进去的是变量值的副本，不是变量本身
  - 原始类型（`int`、`double` 等）：传数值副本，方法内修改不影响外面
  - 引用类型（数组、对象）：传的是引用（地址）的副本，相当于复制了一个指针。副本和原件是两个独立的变量，但存的地址相同、指向同一个对象。因此方法内可以通过引用修改对象内容，但重新赋值（指向别的对象）不影响调用方

- 方法名可以被重载：同一个类里可以有多个同名方法，只要参数列表不同（个数或类型）。编译器根据传入参数的类型自动选择匹配的版本，有歧义时报编译错误。Python 没有此机制，同名函数会直接覆盖

- 方法只能返回一个值，但可以有多个 `return` 语句（不同分支提前返回）。执行到任意一个 `return` 时方法立即结束

- 方法可以产生副作用：副作用指执行过程中对外部状态产生的影响（如打印输出、修改传入的数组、写文件等），与返回值无关。返回值是给调用者的结果，副作用是对外部世界的一切其他影响

### 递归

以二分查找为例：

```Java
public static int rank(int key, int[] a, int lo, int hi) {
    if (lo > hi) return -1;
    int mid = lo + (hi - lo) / 2;
    if (key < a[mid])      return rank(key, a, lo, mid - 1);
    else if (key > a[mid]) return rank(key, a, mid + 1, hi);
    else                   return mid;
}
```

- 递归总有一个最简单的情况（base case）：`lo > hi` 或 `key == a[mid]` 时直接返回，不再递归。没有 base case 会无限递归直到栈溢出
- 递归总是在尝试解决更小的子问题：搜索范围从 `[lo, hi]` 缩小到 `[lo, mid-1]` 或 `[mid+1, hi]`，保证最终能到达 base case
- 递归的父问题和子问题不应该有交集：`mid` 已被检查过，不出现在子问题中。有交集会导致无限递归或重复计算

## 字符串

- `String` 是一串 `char`，用双引号包裹，如 `"Hello, World"`
- 原始类型与字符串用 `+` 拼接时会自动转换：`"" + 3.14` 得到 `"3.14"`
- 也可以显式调用 `String.valueOf(3.14)` 进行转换

## 输入输出

- Java 程序可以从命令行参数或标准输入（`StdIn`）获得输入，并写入标准输出（`StdOut`）
- 标准输入流是顺序消耗的：数据读取后不可重读
- 可以利用 `>` 和 `<` 进行数据流重定向

```shell
java foo > output.txt
java bar < input.txt
```
