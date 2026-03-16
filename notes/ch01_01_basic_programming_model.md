# 基础编程模型

## 数据类型

- Java 有四种原始数据类型：
  - 整型（`int`，32 位）
  - 双精度浮点（`double`，64 位）
  - 布尔型（`boolean`）
  - 字符型（`char`，16 位）

- 如果不损失信息，数值会被自动提升为更高精度的类型
- `char` 本质是 16 位无符号整数，存的是 Unicode 编码值（如 `'a'` = 97）
  - 直接传给 `println`：打印字符（`'b'` → `b`）
  - 参与算术运算（`+` `-` `*` 等）：提升为 `int`，结果是数字（`'b' + 'c'` → `197`）
  - 想拼接字符要用 `String`：`"b" + "c"` → `"bc"`

### 运算符优先级

从高到低（同级从左到右结合）：

| 优先级 | 运算符 | 说明 |
|--------|--------|------|
| 1 | `()` `[]` `.` | 括号、数组下标、成员访问 |
| 2 | `!` `++` `--` `+x` `-x` `(type)` | 一元运算符、类型转换 |
| 3 | `*` `/` `%` | 乘除取余 |
| 4 | `+` `-` | 加减（`+` 也用于字符串拼接） |
| 5 | `<` `<=` `>` `>=` `instanceof` | 比较 |
| 6 | `==` `!=` | 相等判断 |
| 7 | `&&` | 逻辑与（短路） |
| 8 | `\|\|` | 逻辑或（短路） |
| 9 | `=` `+=` `-=` `*=` `/=` `%=` | 赋值（右结合） |

关键点：`&&` 优先级高于 `||`，所以 `a || b && c` 等价于 `a || (b && c)`

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

### 俄罗斯农民乘法与快速幂

两者是同一个递归思路在不同运算上的应用：把 b 拆成二进制，利用加倍/减半避免逐一累加/累乘。

**核心思想**：将 b 按二进制展开。以 `a × b` 为例，`b = 25 = 11001₂ = 16+8+1`，则 `a×25 = a×16 + a×8 + a×1`。每次 b 减半、a 加倍，当 b 为奇数时把当前的 a 累加到结果中。

**俄罗斯农民乘法**（计算 a × b）：

```Java
public static int multiply(int a, int b) {
    if (b == 0)     return 0;
    if (b % 2 == 0) return multiply(a+a, b/2);
    return multiply(a+a, b/2) + a;
}
```

以 `multiply(2, 25)` 为例：

| a | b | b 是奇数？ | 累加 |
|---|---|-----------|------|
| 2 | 25 | 是 | +2 |
| 4 | 12 | 否 | |
| 8 | 6 | 否 | |
| 16 | 3 | 是 | +16 |
| 32 | 1 | 是 | +32 |
| 64 | 0 | base case | return 0 |

结果：0 + 32 + 16 + 2 = 50 = 2 × 25

**快速幂**（计算 a^b）：把 `+` 换成 `*`，`return 0` 换成 `return 1`：

```Java
public static long power(int a, int b) {
    if (b == 0)     return 1;
    if (b % 2 == 0) return power(a*a, b/2);
    return power(a*a, b/2) * a;
}
```

原理相同：`a^b` 中 b 为偶数时 `a^b = (a²)^(b/2)`，b 为奇数时 `a^b = (a²)^(b/2) × a`。

**复杂度**：两者都是 O(log b)——b 每次减半，最多递归 log₂b 次。对比朴素做法的 O(b) 是巨大的提升。

### 进制转换（短除法）

将正整数 N 转换为 B 进制：反复对 B 取余得到最低位，再整除 B 去掉最低位，直到商为 0。先算出的是低位，所以结果要反向拼接。

```Java
// 十进制转二进制
String s = "";
for (int n = N; n > 0; n /= 2)
   s = (n % 2) + s;   // 新位拼到前面
```

以 N=10 为例：`10%2=0` → `5%2=1` → `2%2=0` → `1%2=1`，拼接得 `"1010"`

将 `/2` 和 `%2` 替换为 `/B` 和 `%B` 即可转换为任意进制。

## 字符串

- `String` 是一串 `char`，用双引号包裹，如 `"Hello, World"`
- 原始类型与字符串用 `+` 拼接时会自动转换：`"" + 3.14` 得到 `"3.14"`
- 也可以显式调用 `String.valueOf(3.14)` 进行转换

## 输入输出

### 命令行参数

- `main(String[] args)` 中的 `args` 数组存放命令行参数
- shell 按空格分割参数：`java MyProgram 1 hello 3.14` → `args[0]="1"`, `args[1]="hello"`, `args[2]="3.14"`
- 参数都是 `String`，需要手动转换：`Integer.parseInt(args[0])`、`Double.parseDouble(args[2])`
- 带空格的内容用引号包裹作为一个参数：`"hello world"` → 一个 `args[0]`

### 标准输入（StdIn）

标准输入是一个流（stream），数据像水管一样流过来，**读过就没了，不能回头**。

常用方法：

| 方法 | 作用 |
|------|------|
| `StdIn.readInt()` | 读一个 int |
| `StdIn.readDouble()` | 读一个 double |
| `StdIn.readString()` | 读一个单词（以空白字符分隔） |
| `StdIn.readLine()` | 读一整行 |
| `StdIn.isEmpty()` | 是否还有输入 |

`readInt()`、`readDouble()`、`readString()` 以**空白字符**（空格、换行、tab）作为分隔符，换行和空格没有区别。例如输入：

```
Alice 1 2
Bob 3 4
```

连续调用 `readString()`, `readInt()`, `readInt()` 依次得到 `"Alice"`, `1`, `2`，下一轮同理得到 `"Bob"`, `3`, `4`。

`isEmpty()` 只是检查后面还有没有数据（peek），**不消耗**任何内容。只有 `readXxx()` 才消耗。因此标准的流式读取模式是：

```Java
while (!StdIn.isEmpty()) {
    String name = StdIn.readString();
    int a = StdIn.readInt();
    int b = StdIn.readInt();
    // 处理这一组数据
}
```

不需要提前知道有多少行，来多少处理多少，直到 EOF 结束。

### 数据来源

| 方式 | 命令 | EOF 信号 |
|------|------|---------|
| IntelliJ 控制台 | 运行后在 Run 窗口输入 | `Ctrl+D` |
| 文件重定向 | `java MyProgram < input.txt` | 文件读完自动 EOF |
| 管道 | `echo "1 2 3" \| java MyProgram` | 管道结束自动 EOF |

### 标准输出与重定向

```shell
java foo > output.txt
java bar < input.txt
```
