# Rust 
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
## 问题
```rust
let mut guess = String::new();
loop{
io::stdin().read_line(&mut guess).expect("无法读取");
}
```
不能重新read