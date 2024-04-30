# Java-1

* [notes.Java](#java.)
  * [idea](#idea.)
  * [类转型&多态&契约设计](#类转型多态契约设计.)
  * [static、final 和常量设计](#static、final-和常量设计.)
    * [static](#static.)
    * [单例模式](#单例模式.)
    * [final](#final.)
    * [常量&常量池](#常量常量池.)
    * [不可变对象](#不可变对象.)
  * [notes.Java 访问权限](#java-访问权限.)
  * [notes.Java 类库](#java-类库.)
    * [数字类](#数字类.)
    * [字符串类](#字符串类.)
    * [时间类](#时间类.)
    * [格式化类](#格式化类.)
    * [Exception](#exception.)
  * [异常分类](#异常分类.)
  * [异常处理](#异常处理.)
  * [自定义异常](#自定义异常.)
  * [数组](#数组.)
  * [JCF](#jcf.)
    * [List](#list.)
    * [Set](#set.)
      * [java中的集合接口Set](#java中的集合接口set.)
    * [Map](#map.)
    * [Util](#util.)
    * [File](#file.)
  * [javaIO](#javaio.)
    * [File](#file-1.)
    * [文件读写](#文件读写.)
      * [写入文件](#写入文件.)
    * [二进制文件读写](#二进制文件读写.)
          * [](Java_#.)
    * [zip读入输出](#zip读入输出.)


# notes.Java

## idea

- ctrl+shift+v:替换变量名
- ctrl+alt+v:抽取并赋值
- ctrl+p:查看参数
- ctrl+alt+o删除多余包
- ctrl+F12 查看当前类中的包
- ctrl+shift+enter 自动完成当前语句
- ctrl+shift+backspace 返回到上次修改位置
- alt+shift+F9 Debug

## 类转型&多态&契约设计

1. 类转型：子类可以转父类，父类不可以转子类（除非父类对象本身就是子类）
2. 多态：子类转型为父类后，调用普通方法，依旧是子类方法
3. 契约设计：类不会直接使用另一个类，而是采用接口的形式，外部可以“空投”这个接口下的任意子类对象

## static、final 和常量设计

### static

1. static 变量：不管有多少个对象，内存中只有一份
2. static 方法：可以使用类名直接引用，无需 new 对象来引用
   - 静态方法不可以调用动态变量
   - 非静态方法可以调用静态方法
   - 静态方法不可调用非静态方法
3. static 块：static 块只执行一次，并且 static 块>匿名块>构造函数

### 单例模式

1. 设计模式：是经过验证的、用于某些特定场合的解决方案
2. GOF 提出 23 中设计模式；创建型、结构性和行为型
3. 单例模式保证一个类在内存空间中只有一个对象
   - 内部初始化一个单例
   - private 不可见，getInstance(static 方法)来取出单例对象

### final

1. final 类：没有子类继承
2. final 方法：不能被子类改写（不能重写，可以重载）
3. final 字段：基本类型被能修改值，对象类型不能修改指针（可以修改内容）

### 常量&常量池

1. java 中的常量：static 和 final
2. notes.Java 接口中的变量都是常量
3. 对象生成有两种：常量赋值（栈内存）和 new 创建（堆内存）
4. 常见包装类
   ![包装类](2020-03-11-16-34-12.png)
5. java 编译器会优化已经确定的变量
6. 栈内存与堆内存运算会产生新的堆内存（存疑）

### 不可变对象

1. 不可变对象提高读效率
2. 不可变对象设计的方法
3. 字符串 append 操作速度：StringBuilder>StringBuffer>String

## notes.Java 访问权限

![权限](2020-03-11-17-43-27.png)

## notes.Java 类库

### 数字类

1. 根据数字特点选择合适的类
2. 尽量使用类库自带的方法
3. 整数
   ![整数](2020-03-12-17-36-09.png)

4. 浮点数需要注意精度
   ![浮点数](2020-03-12-17-32-39.png)

5. BigInteger
   - 支持无限大的整数运算
     ![BigInteger](2020-03-12-18-45-07.png)
6. BigDecimal -支持无限大的小数运算
   - 注意精度和截断
     ![BigDecimal](2020-03-12-17-51-58.png)
7. 随机数

![随机数](2020-03-11-18-08-16.png)

### 字符串类

1. String（不可变对象，只读）
2. StringBuffer（字符串加减，同步，性能好）
3. StringBuilder（字符串加减，不同步，性能更好）

### 时间类

1. 当前多数程序还是使用 calendar 类处理时间

![方法](2020-03-11-18-07-28.png)
2. DateUtil，Instance 时间戳

### 格式化类

![格式化类](2020-03-11-18-29-52.png)
1. NumberFormat
    ![DecimalFormat](DecimalFormat.png)
2. MessageFormat
    ![MessageFormat](MessageFormat.png)
3. DateFormat
    ![DateFormat](DateFormat.png)
    ![DateFormat](2020-03-11-20-00-15.png)
4. DateFormatter
    ![DateFormatter](DateFormatter.png)   

### Exception

## 异常分类

- 异常：程序不正常的行为或状态
- 异常处理：
    1. 程序返回到安全状态
    2. 允许用户保存结果，并以适当方式关闭程序
- 异常分类
- ![异常分类](异常分类.png)   
    1. Throwable:所有错误的祖先
    2. Error：系统内部错误或者资源耗尽
    3. Exception：程序有关错误
- 又可分为unchecked异常和checked异常，
- 编译器会辅助检查checked异常    

## 异常处理
![try&catch](try&catch.png)    
try：正常逻辑代码
catch：当try发生异常，将执行catch代码，若无异常，绕之
finally：当try或者catch执行后，必须要执行finally
- ![try](try.png)
- 一个try只会进入一个catch，优先级从上而下
![catch](catch.png)

throws:抛出异常
- ![Throws](Throws.png)
- 一个方法被覆盖，覆盖他的方法必须抛出相同的异常，或者异常的子类
- 如果父类方法抛出多个异常，那么重写的子类方法必须抛出那些异常的子集，也就是不能抛出新的异常
    - ![父类](父类.png)
    - ![子类](子类.png)    
    
## 自定义异常

- 自定义异常，需要继承Exception类或者其子类
    - 继承自Exception就变成CheckedException
    - 继承自RuntimeException，就变成Unchecked Exception
- 自定义重点在构造函数
    - 调用父类Exception的message构造函数
    - 可以自定义自己的成员变量
- 在程序中采用throw主动抛出异常
    ![代码](代码.png) 
    - 异常抛出测试
    - ![代码](daa3ad77.png)
## 数组

1. 数组是一种确定大小的储存同种数据的容器
2. 初始化和遍历方法

## JCF

1. 容器框架的作用
2. JCF主要数据结构
    - 列表
    - 集合
    - 映射

### List
1. ArrayList ：for循环遍历最快，迭代器最慢 索引位置适中
    -  遍历快
2. LinkedList ：for循环遍历最快，迭代器适中，索引位置极慢
    - 插入快
3. Vector（同步）
    - 和ArrayList类似，可变数组实现的列表
    - Vector同步，适合再 ==多线程== 下使用
    
    
- 迭代器
    - ![迭代器](迭代器.png)
###  Set

- 确定性：对任意对象都能判定其是否属于某一集合
- 互异性：集合内每个元素都是不相同的，<u>注意是内容互异</u>
- 无序性：集合内的顺序无关

#### java中的集合接口Set

- HashSet(基于散列函数的集合，无序，不支持同步)
    ![HashSet](HashSet.png)
    - 无序，遍历for快于iterator
    
- LinkedHashSet(基于散列函数和双向链表的集合，可排序的，不支持同步)
    ![LinkedHashSet](LinkedHashSet.png)
    - 存储数据是插入的顺序序，for快于iterator

- TreeSet(基于树结构的集合，可排序的，不支持同步)
    ![TreeSet](TreeSet.png)
    - 不可有null，按着从小到大的顺序存储，for快于iterator
    
> HashSet&&LinkedHashSet 的比较是通过hashcode比较的，若想要new的新对象根据自定义要求去重，重写hashcode方法
>
>   1. 判定两个元素的hashCode是否相同，若不同，返回false
>   2. 若两者hashCode相同，判定equals方法，若不同，返回false，否则返回true
>   3. hashCode和equals方法是所有类都有的，因为Object类有
>![Override](9692a1c5.png)
>TreeSet判断重复利用compareTo方法，实体类需要继承Comparable接口，然后重写CompareTo方法  
> - 补充：Integer类重写了CompareTo方法
>   ![CompareTo](11c71953.png)
    
### Map

- Hashtable 同步，慢，数据量小   t是小写
    ![Hashtable](Hashtable.png)
    ![Iterator遍历](9d857dc3.png)
- HashMap 不支持同步，快，数据量大
    ![HashMap](HashMap.png)
    - 遍历有iterator方法和KeySet方法（hashMap.get(key)）,KeySet方法速度更快
- LinkedHashMap
    - 基于双向链表的维持<u>插入顺序</u>的HashMap
- TreeMap
    - 基于红黑树的Map，可以根据key的自然排序，按照key的从大到小或者compareTo方法进行排序输出
- Properties 同步，文件形式，数据量小    
    ![Properties](Properties.png)
    ![get&writeProperties](get&writeProperties.png)
    ![main&getValue](main&getValue.png)

### Util

- Arrays  
    ![Arrays](Arrays.png)  
       - 补充（fill(数组名，开始位数，结束位数，值))  
    - Arrays.asList(str.split("reg"))  将字符串转化为list
        - String.join("reg",List) 将list转化为字符串
- Collections  
    ![](021a01f2.png)  
    -对象的比较  
    ![comparable](eb55ee78.png)  
    
### File

- ![文件概述](27479294.png)
- 文件系统和java是并列的两套系统
- File类是文件基本属性操作的主要类
- java7提出了NIO包在某些功能上有重要的补充作用
-   ![testPath](testPath.png)
-   ![testFile](testFile.png)
-   ![testFiles1](testFiles1.png)
-   ![testFiles2](testFiles2.png)

## javaIO



![思维导图](a65fcb1f.png)

- java文件处理类都在java.io包中
- 处理类分成：节点类、包装类（转化类、装饰类）

### File

- ![文件概述](27479294.png)
- 文件系统和java是并列的两套系统
- File类是文件基本属性操作的主要类
- java7提出了NIO包在某些功能上有重要的补充作用
-   ![testPath](testPath.png)
-   ![testFile](testFile.png)
-   ![testFiles1](testFiles1.png)
-   ![testFiles2](testFiles2.png)

### 文件读写

#### 写入文件

- 写文件
    - 先创建文件，写入数据，关闭文件
    - FileOutputStream,OutputStreamWriter,BufferWriter
    - BufferWriter
        - write
        - newLine
    - try-resource语句，自动关闭资源
    - 关闭最外层的数据流，将会把其上所有的数据流关闭

```java
 public class testWrite {
     public static void main(String[] args) {
         method_1();
 //        method_2();
 
     }
 
     public static void method_1() {
         FileOutputStream fileOutputStream = null;//节点类，负责写字节
         OutputStreamWriter outputStreamWriter = null;//转化类，负责字符到字节的转化
         BufferedWriter bufferedWriter = null;//装饰类，负责写字符到缓存区
         //三者关系bufferedWriter(OutputStreamWriter(FIleOutputStream)))
 
         try {
             fileOutputStream = new FileOutputStream("./temp/abc.txt");
             outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
             bufferedWriter = new BufferedWriter(outputStreamWriter);
 //            bufferedReader = new BufferedReader(new OutputStreamWriter(new FileOutputStream("./temp.abc")));
             //一句话的写法
             outputStreamWriter.write("我们是");//TODO 存疑 javaIO可用outputStreamWrite来输出？
             bufferedWriter.newLine();
             bufferedWriter.write("method_1");
             bufferedWriter.newLine();
 
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             try {
                 bufferedWriter.close();//关闭最后一个，会将所有的底层流全部关闭
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
         }
     }
 
     public static void method_2() {
         //try-resource语句,自动关闭资源
         try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./temp/abc.txt")))) {
             bufferedWriter.write("我们是");
             bufferedWriter.newLine();
             bufferedWriter.write("method_2");
             bufferedWriter.newLine();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }

```

- 读文件
    - 先打开文件，逐行读入数据，关闭文件
    - FileInputStream,InputStream,BufferedReader
    - BufferReader
        - readLine
    - try-resource语句，自动关闭资源
    - 关闭最外层的数据流，将会把其上所有的数据流关闭

```java
public class testRead {
    public static void main(String[] args) {
        method_1();
        System.out.println("===============");
        method_2();
    }

    public static void method_1() {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line = "";
        try {
            fileInputStream = new FileInputStream("./temp/abc.txt");
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
//            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("./temp/abc.txt")));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void method_2() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("./temp/abc.txt")))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
尽量使用try-resource方法，自动关闭资源

### 二进制文件读写

####写入

- 写文件
    - 先创建文件，写入数据，关闭文件
    - FileOutputStream,BufferedOutputStream,DataOutputStream
    - DataOutputStream
        - flush 
        - write/writeBoolean/writeByte/writeChars/writeDouble/writeInt/writeUTF/...
    - try-source 语句，自动关闭资源
    - 关闭最外层数据流，将会把其上所有的数据流关闭
```java
public class testBinWrite {
    public static void main(String[] args) {
        method_1();

    }

    public static void method_1() {
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("./temp/def.dat");
            dataOutputStream = new DataOutputStream(fileOutputStream);
            bufferedOutputStream = new BufferedOutputStream(dataOutputStream);

            dataOutputStream.writeUTF("a");
            dataOutputStream.writeInt(222);
            dataOutputStream.writeUTF("b");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
```

- 读文件
    - 先打开文件，读入数据，关闭文件
    - FileInputStream,BufferedInputStream,DataInputStream
    - DataInputStream
        read/readBoolean/readInt/readChar/readDouble/readFloat/readUTF/...
    - try-resource语句，自动关闭资源
    - 关闭最外层的数据流，将会把其上所有的数据流关闭
    
```java
public class testBinRead {
    public static void main(String[] args) {
        method_2();
    }

    //try-resource
    public static void method_2() {
        try (DataInputStream dataInputStream = new DataInputStream( new BufferedInputStream(new FileInputStream("./temp/def.dat")))) {
            String a = dataInputStream.readUTF();
            int b = dataInputStream.readInt();
            String c = dataInputStream.readUTF();
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

```
总结：
    - 理解节点类，转换类，包装类的联合用法
    - 读取需要根据写入的规则进行读取，避免错位
    - 尽量使用try-resource语句，自动关闭资源
    
### zip读入输出

[zip](https://www.bilibili.com/video/av89754609?p=46)
        
