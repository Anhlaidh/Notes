# Rust
[Rust权威指南](https://kaisery.github.io/trpl-zh-cn/ch05-03-method-syntax.html)
## 特点
  - 预编译
  - 强类型
  - 类型隐藏
### 与java不同点
  - 变量不能二次赋值
    - 如果需要二次赋值，需声明mut，即 `let mut [var]`
      > Rust认为，应该显式地区分可变于不可变，声明可变说明允许变量被修改, 默认不可变是因为，根据统计，一般而言不被修改的变量要比被修改的变量多
      > 不可变变量，有利于防止一些傻*修改了本来不应该修改的值，导致发生灾难
  - 占位符 {}  类比log4j
    - 需要实现std::fmt::display
    - 结构体打印可用`{:?}`或`{:#?}`
      - 需添加std::fmt::Debug
      - `#[derive(Debug)]`
  - 在需要类型转换时，可以对同一个变量多次声明
  - move
    > 为了保证内存安全，Rust不会尝试复制被分配的内存
    > 
    > 以string为例，s1="abc"，s2=s1时，在java中，s2，s1都会指向heap的"abc"，且只有s1，s2都出作用域之后，对"abc"进行gc操作
    > 
    > 某些语言中，会在s1离开作用域后，进行一次drop操作，s2离开时会再重复一次，导致二次回收的bug
    > 
    > 在rust中，为了解决这个问题，在s2=s1之后，s1就失效了，此时调用s1，则会 `value borrowed here after move`
    - 在指针传入函数后，相当于进行了move操作，就失效了
    - 返回值也会有所有权转交的性质
    - 一旦struct的实例是可变的，那么实例中所有字段都是可变的
    - 枚举类型变体可以为不同的类型
      > ```
      > enum IpAddressKind{
      >     V4(u8,u8,u8,u8),
      >     V6(String)
      > }
      > ```
      > 
    - rust没有null
    - rust 所有条目默认为private
    - rust UT允许测试私有函数 
    - 文档注释支持markdown

 ## 入门
- IDE vscode
- 振奋人心的hello world
- 编译命令 rustc 生成二进制文件
### cargo
```toml
[package]
name = "rustLearning"
version = "0.1.0"
edition = "2021"

# See more keys and their definitions at https://doc.rust-lang.org/cargo/reference/manifest.html

[dependencies]
rand = "0.8.5"
```
- 创建项目：cargo new 
    - Cargo.toml
      - Cargo配置文件
        - 依赖列表
    - src
      - 源代码
    - Cargo.lock
- 构建项目
  - cargo build
    - 生成cargo.lock文件
      - 精确依赖版本
    - cargo run 编译+执行二进制文件 /target/debug
    - cargo check 编译（比build快)
    - cargo build --release 发布构建 /target/release
- 依赖管理
  - Cargo.lock
  - Cargo.toml
  - Cargo build
    - 不修改版本号时，则根据Cargo.lock 编译
    - 否则更新依赖
  - Cargo update
    - 根据 toml编译，并将Cargo.lock更新为当前版本的最新小版本，[0.8.max]
### DEMO
- 生成随机数小游戏
- 声明变量 let
  - 显式声明变量类型 `let [var] : [type]`
    - let guess: u32
- io::stdin
- match
  - 
- 循环
  - loop
    > 使用loop进行无限循环，而不是while ture，while在rust中侧重于condition，而不是true，体现了rust的设计哲学：高度一致性
    > 正如match中最后一个ram后面，可选择加逗号，语句一致性

### Rust
#### 变量常量
- 变量与可变性
  - 声明变量用let
  - 默认情况变量不可变（immutable)
  - `cannot assign twice to immutable variable`
- 常量
  - 不可以用mut
  - 用const关键字，类型必须被标注
  - 可以在任何作用域声明，包括全局作用域
  - 只可以绑定到常量表达式，无法绑定到函数的调用结果或只能在运行时才能计算的值  ==>静态值？
  - 命名规范与java相同
- shadowing
  - 可以使用相同的名字声明新的变量，新的变量会shadow之前的变量
  - 后续代码中这个变量名则代表新的变量
#### 数据类型
- 标量类型和复合类型
  - 基于使用的值，编译器通常能推断出具体类型
  - 可能的类型比较多，则需要添加类型标注
  - 标量类型
    - 一个标量类型表示一个单一的值
    - 四种类型
      - 整数类型
        - isize usize
        - 除了byte之外，都允许使用类型后缀，57u8
        - 默认i32
        - 整数溢出
          - 把u8的值设为256
            - 调试模式下编译：Rust检查整数溢出，如果发生溢出，则程序在运行时会panic
            - 发布模式下（--release）编译，rust不会检查可能导致Panic的整数溢出
              - 如果发生溢出，rust会执行"环绕"操作
                - 256变成0，257变成1
                - 但程序不回panic
      - 浮点类型
        - f32
        - f64 默认
      - bool
        - true
        - false
      - 字符
        - 与java相同
  - 复合类型
    - 将多个值放在一个类型里面
    - rust提供两种基础的复合类型：元组（tuple）、数组
      - Tuple
        - Tuple可以将多个类型的值放到一个类型里面
        - Tuple的长度是固定的，一旦声明无法改变
      - 数组
        - 数据存放在栈(stack)内存上
        - 没有vector灵活
        - `let [var]:[type;length]` 例如：`let a:[i32:5]`
          - 设置默认值：`let a=[3;5]`即 `let a=[3,3,3,3,3]`
          - 超出数组范围时，编译不报错，运行时panic
#### 函数
```rust
fn function(x: i32, y: String) {
}
```
- 命名规范 python xx_xx
- 参数
- 语句与表达式
  - 语句无返回值，不能用let将一个语句赋值给一个变量
- 函数返回值
  - 在->符号后面声明函数返回值的类型，但是不能为返回值命名
  - rust里，返回值就是函数体最后一个表达式的值
  - 若想提前返回，需要return
#### 控制流
##### if else
```rust
if x > 5 {
        return y;
    }else {
        return x.to_string() 
    }
```
- if是表达式，可以放在 = 右边，给变量赋值
```rust
return if x > 5 {
        y
    } else {
        x.to_string()
    }
```
##### 循环
- loop
- while
- for
  - 遍历集合
    - `for element in a.iter(){}`
  - range
    - 标准库提供
    - 指定一个开始和结束，range生成他们之间的数字（左闭右开）
    - rev方法可以反转range
    - `for number in (1..4).rev(){}`
#### 所有权
    
- rust核心特性
- 解决垃圾回收，内存释放
  - 对于某个值来说，拥有它的变量走出作用范围时，内存会立即自动交还给操作系统
- 内存管理提前到了编译阶段
##### Stack vs Heap
- Stack vs Heap
  - 写数据
    - 把值压到stack上不叫分配
    - 指针在stack上
    - 数据压到stack上比heap快很多
    - heap上分配空间需要更多工作
  - 读数据
    - 类比java内存模型
##### 所有权规则
  1. 每个值都有一个变量，这个变量是这个值的所有者
  2. 每个值同时只能有一个所有者
  3. 当所有者超出作用域（scope）时，该值将被删除
- 变量和数据的交互方式 ：move
  > s1="abc" s2=s1 s1失效
  - 浅拷贝 
  - 深拷贝 clone
   - rust中在将s2指针指向"abc"后，对s1进行了失效操作，对此称为"move"
- copy trait 可以用于像整数这样完全存放在stack上面的类型  
  - 除了四种标量类型，tuple如果都是标量，那么也具有copy trait

- 总结： 在一个作用域结束的时候，heap上的值要么被drop掉，要么被转交
- 那么如何让函数使用某个值，但不获取所有权
  1. 函数传入自己，再返回元组的时候把自己返回出来（非常麻烦）
  2. 引用
###### 引用 借用
- `&`表示引用
  
- 规则
  - 在任何给定的时刻，只能满足下列条件之一
    - 一个可变的引用
    - 任意数量不可变的引用
      - 可以通过创建新的作用域，来允许非同时创建多个可变引用
  - 引用必须一直有效
- 引用跟变量一样，默认是不可变的，同时可以加mut转为可变引用
- 借用
  - 把引用作为函数参数的行为叫做借用
- 悬空引用（Dangling pointer）
  - 一个指针引用了内存中的某个地址，而这块内存可能已经释放并分配给其他人使用了
  - rust编译器保证引用永远都不是悬空引用
    - 如果你引用了某些数据，编译器将保证在引用离开作用域前数据不会离开作用域
###### 切片
- rust的另一种不持有所有权的数据类型：切片
- slice 允许你引用集合中一段连续的元素序列，而不用引用整个集合
- 字符串字面值就是 slice
###### struct
- 类比thrift
- 一旦struct的实例是可变的，那么实例中所有字段都是可变的
- （语法糖）字段值跟变量名相同时，构造时可以简写
- struct更新语法
- tuple struct
  - 整体有名，元素没有名
- unit-like struct
  - 没有任何字段
- 默认拥有所有字段所有权
  - 也可以拥有引用，需要生命周期保证
- 定义方法
  - impl [struct_name]
###### 枚举

- 枚举类型变体可以为不同的类型
- 枚举方法 
- Option
  - 标准库
  - 某个值可能存在（某种类型）或不存在的情况
  - 内容
    - some
    - None


###### match
- 必须穷举所有的可能
  - `_`通配符表示其余的可能，default
###### if let
- match 一个（模式匹配）
#### 代码组织
- lib项目
  - cargo new xxx --lib
- main项目
  - cargo new xxx

##### super pub struct enum
- 默认private，因此需要叫pub关键字
- enum加完pub，所有变体都将pub
#### 集合 
- Vector
  - Vec<T> 类比java new 一个list
  - 初始宏： `let v = vec![1,2,3];`
  - 写
    - push
  - 读
    - 索引 编译时会检查索引是否超出，类比数组
    - get 返回option<T>
- String
  - String是对Vec<u8>的包装
  - 不能直接索引
  - bytes for遍历
  - chars for遍历 字型蔟
  - 默认utf-8 
  - 可以切片 对于可变字节切片，遇到边界问题，程序会panic
  - HashMap <k,v>
    - HashMap::new()
      - 写
        - insert方法会move所有权，当然也可以传引用
          - 相同key则覆盖
        - entry or_insert()
          - entry方法返回是否存在对应key
    - zip collect
    - ```rust
      fn main() {
        let teams = vec!["Blue".to_string(), "Yellow".to_string()];
        let scores = vec![10, 20];
        let map: HashMap<_, _> = teams.iter().zip(scores.iter()).collect();
        let string = format!("{:?}", map);
        print!("map: {}", string);
      }

      ```
      > zip()接受一个参数，将调用者中的元素与参数中的元素一一对应组成Tuple，若数量不匹配，多的元素会丢掉
      > collect()方法在形成了一个HashMap，元素的顺序并不固定，每一次run都可能不一样
      > 当然key -> value的顺序是由zip一一对应的，不是有collect决定的，请搞清楚
      > 
    
#### 错误处理
- panic
  - 自己代码中
  - 依赖中
  - 可以调用panic的回溯信息来定位问题代码
  - 设置环境变量 RUST_BACKTRACE=1可得到回溯信息
    - MacOS使用： RUST_BACKTRACE=1 cargo run 
- Result枚举
  - match处理
    - unwrap 成功则返回对应值，否则panic，不能指定panic信息
    - expect 成功则返回对应值，否则panic，能指定panic信息
- 传播错误 （throw e） 
  - return Result<T,io::Error>
  - ? 运算符
    - 只能用于返回Result<T,E>的函数
    - main函数返回类型也可以为 Result<T,E>, `Result<(),Box<dyn Error>>`
      - `Box<dyn Error>>`可以简单理解为"任何可能的错误类型"
  - ?与from
- 什么时候用panic
#### 泛型 trait 生命周期
- 泛型
- Trait 把方法签名放在一起来定义实现某种目的所必须的一组行为（类比interface）
  - 关键字 trait
  - 只有方法签名，没有具体实现（可以有默认实现）
  - trait可以有多个方法：每个方法签名占一行，以`;`结尾
  - 实现trait的类型必须提供具体的方法实现
  - impl trait_name for struct_name{}
  - 实现trait的约束
    - 可以在某个类型上实现某个trait的前提条件是：这个类型或这个trait是在本地crate里定义的
    - 无法为外部类型实现外部的trait
      - 这个限制是程序属性的一部分：一致性
      - 孤儿规则：之所以这样命名是因为父类型不存在
      - 此规则确保其他人的代码不能破坏你的代码，反之亦然
      - 如果没有这个规则，两个crate可以为同一个
    - 作为参数 
      - `fn a(a: impl trait_name +trait_2_name) -> i32 {}`
      - trait bound写法  `fn notify<T: trait_name+trait_2_name >(item: T){}`
        - where写法  `fn notify<T >(item: T) where T:trait_1_name+trait_2_name,...{}`
    - 作为返回值 `fn a()-> impl trait_1_name+trait_2_name`
      - impl trait 只能返回确定的同一种类型，返回可能不同的类型的代码会报错
- 生命周期
  - rust的每个引用都有自己的生命周期
  - 借用检查器
  - 生命周期：引用保持有效的作用域
  - 大多数情况：生命周期是隐式的、可被推断的
  - 当引用的生命周期可能以不同的方式相互关联时：手动标注生命周期
  - 语法
    - 形如`'a` 以`'`开头，
    - 生命周期标注位置（个人理解：以调用出发，先考虑传进去引用的参数的作用范围，再考量函数中生命周期的标注）
      - 在引用的&后
      - 空格将标注与引用类型分开
  - 生命周期省略的三个规则（同样适用于fn和impl）
    1. 每个引用类型的参数都有自己的生命周期
    2. 如果只有1个输入生命周期参数，那么该生命周期被赋予给所有的输出生命周期参数
    3. 如果有多个输入生命周期参数，但其中一个是&self或&mut self（是方法），那么self的生命周期会被赋予给所有的输出生命周期参数
  - 结构体方法生命周期语法
    - 
  - 静态生命周期 `'static`
#### 测试
  
- cargo test
  - 会执行 包含`#[Test]` attribute的方法
    - `#[Ignore]`
  - 默认行为
    - 并行运行
      - 确保不回相互依赖，不依赖共享状态，或指定单线程
    - 所有测试
  - 命令行参数
    - --test-threads
    - cargo test --help
    - cargo test -- --help
- 测试失败
  - panic!
  - Assert
    - assert! 判断true，false
    - assert_eq! & assert_nq!
    - 自定义错误信息，最后一个参数传打印内容，支持format
- `#[should_panic]`
  - 发生panic成功，未发生则失败
  - expected 标注panic的返回信息
- Result<T,E>

- 单元测试   
  - mod标注 `#[cfg(Test)]`
  - rust允许测试私有函数
- 集成测试
  - tests目录 cargo test时才会编译
    - common 目录 tests的公共目录，不会被视为test crate
- binary crate 的集成测试
  - 没有lib.rs时 不能 在tests下创建集成测试，无法把main.rs的函数导入作用域
#### 命令行程序DEMO
 
#### 闭包
- 闭包
  - 匿名函数
  - 保存为变量、作为参数
  - 可在一个地方创建闭包，然后在另一个上下文中调用闭包来完成运算
  - 可从其定义的作用域捕获值
#### 迭代器
  
- 类比java函数式编程
#### cargo crates.io
- 类比maven?
  - profile
- cargo doc 
#### 智能指针
##### Box<T>
- 特点
  - 允许在heap上存储数据
  - stack上是指向heap数据的指针
  - 没有性能开销以及其他额外功能
- 场景
  - 编译时大小无法确定，上下文需要知道确切大小
  - 大量数据要移交所有权，需要确保操作时数据不会被复制
  - 使用某个值只关心是否实现了特定trait，不用关心具体类型
    - 例如"链表" Cons list
      - 用Box来包装
##### Deref 
      
- 例如String 和&str?
  - 在参数判断类型时发生不一致，发生解引用
  - MyBox实现了deref trait，且MyBox是封装的String，那么MyBox可以被解引用为&str
      
- 具有解引用的能力
- 本质上是 *（deref())，所以返回的是引用类型

- 解引用与可变性

- drop trait
  - 自定义值将要离开作用域时发生的动作
  - drop(c) 手动清理内存
- Rc<T> 引用计数 
  - Rc::clone(&a) 增加引用 不可变引用
    - 与clone方法的区别，Rc clone是clone指针，标准拷贝，clone是深拷贝
  - Rc::strong_count(&a) 获得引用计数（强引用）
    - weak_count 弱引用
- 内部可变性 允许在只持有不可变引用的前提下对数据进行修改，unsafe
- RefCell<T>
#### 无畏并发
- thread::spawn
  - 返回类型 joinHandle
- 消息传递
  - go：不要用共享内存来通信，要用通信来共享内存
  - Channel 进行通信，支持多个发送方，一个消费方
  - 也支持共享内存
    - Mutex 相当于多线程中的RefCell,需要先lock，再使用
      - 用Arc<T>封装使用，不能用Rc :Rc不支持多线程，需要实现Send方法才可以多线程
- Send和 Sync trait
  - 自己实现并发
  - Send 允许线程间转移所有权
    - 实现Send trait 的类型可在线程间转移所有权
    - Rust中几乎所有类型都实现了Send，除了Rc
    - 任何完全由Send类型组成的类型也被标记为Send
    - 除了原始指针之外，几乎所有的基础类型都是Send
  - Sync 允许从多线程访问
    - 实现Sync的类型可以安全的被多个线程引用
    - T是Sync 那么&T就是Send
      - 引用可以被安全的送往另一个县城
    - 基础类型都是Sync
    - 完全由Sync类型组成的类型也是Sync
    - 
## 问题
```rust
let mut guess = String::new();
loop{
io::stdin().read_line(&mut guess).expect("无法读取");
}
```
不能重新read

```rust
fn main() {
    let mut v = vec![1, 2, 3, 4, 5];
    let a = &v[0];
    // v.push(6);
    println!("{}", a);
}
//如何在取完v[0]引用之后再像v中添加元素
```



## 附录
vec->array
```rust
 #[test]
fn aes_test() {
  let decode = &base64::decode("MTIzNDU2Nzg5MDEyMzQ1Ng==").unwrap()[..];
  let vec = <& [u8;16]>::try_from(decode);
  println!("{:?}", vec);
}
```