# Java-3

* [notes.Java](#java.)
  * [语法糖 Syntactic sugar](#语法糖-syntactic-sugar.)
    * [jdk版本](#jdk版本.)
    * [for/for-each](#forfor-each.)
    * [enum类型](#enum类型.)
    * [不定项参数](#不定项参数.)
    * [静态导入](#静态导入.)
    * [自动装箱和拆箱](#自动装箱和拆箱.)
    * [多异常并列](#多异常并列.)
    * [数字增强](#数字增强.)
    * [接口方法](#接口方法.)
    * [try-with-resource](#try-with-resource.)
    * [ResourceBundle文件加载](#resourcebundle文件加载.)
    * [var类型](#var类型.)
    * [switch](#switch.)
  * [反射 reflection](#反射-reflection.)
  * [编译器API](#编译器api.)
    * [JavaCompiler](#javacompiler.)
  * [代理(Proxy):代替处理](#代理proxy代替处理.)
  * [AOP(Aspect Oriented Programming)](#aopaspect-oriented-programming.)
    * [面向切面编程](#面向切面编程.)
  * [注解Annotation](#注解annotation.)
    * [自定义注解](#自定义注解.)
    * [元注解](#元注解.)
  * [嵌套类 Nested classes](#嵌套类-nested-classes.)
  * [Lambda表达式](#lambda表达式.)
    * [函数式接口](#函数式接口.)
    * [系统自带的函数式接口](#系统自带的函数式接口.)


# notes.Java

## 语法糖 Syntactic sugar

### jdk版本
- 编译级别
- 兼容级别
    - 编译后高版本可以运行低版本字节码
    - 反之不行
### for/for-each
- for
    - `for(int i=0;i<nums.leng;i++)`
    - 可以删除元素
- for-each
    - `for(String i:hs)`
    - 5.0开始存在
    - 语法简洁
    - 不能删除元素
    - 只能正向遍历
    - 不能同时遍历两个集合
### enum类型
- java5 推出
- enum有多少个值就有几个实例对象
- 可以添加属性/构造函数/方法
- 构造函数只能是package-private(default)或者private,内部调用
enum类型
    - 所有enum类型都是Enum的子类,也继承了相应方法
    - ordinal()返回枚举所在的索引位置,从0开始
    - compareTo()比较两个枚举值的索引位置大小
    - toString()返回枚举值的字符串标识
    - valueOf()将字符串初始化为枚举对象
    - values() 返回所有的枚举值
```java
package notes.Java.Java_Final.suger;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-05 21:31
 */
public class testEnum {
    public static void main(String[] args) {
        Fruit a1 = Fruit.APPLE;
        System.out.println("Price is" + a1.price);

        System.out.println("====================");
        Day d1 = Day.MONDAY;
        Day d2 = Enum.valueOf(Day.class, "MONDAY");
        System.out.println(d1 == d2);
        Day d3 = Enum.valueOf(Day.class, "TUESDAY");
        System.out.println(d1.compareTo(d3));

        //遍历
        for (Day item : Day.values()) {
            //输出索引值
            System.out.println(item.toString() + "," + item.ordinal());
        }
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    enum Fruit {
        APPLE(10), ORANGE(8);
        private int price;

        Fruit(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }


}

```
### 不定项参数
- JDK5提供了不定参数(可变参数)功能
    - 类型后面加3个点,入int.../double.../String...
    - 可变参数,本质上是一个数组
    - 一个方法只能由一个不定项参数,且位于参数列表的最后
    - 重载的优先级规则1:固定参数的方法,比可变参数优先级更高
    - 重载的优先级规则2:调用语句,同时与两个带可变参数的方法匹配,则报错
```java
package notes.Java.Java_Final.suger;

/**
 * @Description: 可变参数
 * @author: Anhlaidh
 * @date: 2020-07-05 21:46
 */
public class testVariableArgument {
    public static void main(String[] args) {
        print();
        print("aaa");
        print("aaa","bbb");
        print("aaa","bbb","ccc");

    }

    private static void print(String... args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    //当只有一个参数时,本方法优先级更高
    private static void print(String s) {
        System.out.println("优先级高的方法");
    }

    //错误:一个方法不可以有多个可变参数
//    public static void print(String... args, int... irgs) {
//
//    }
    //调用语句,同时与两个带可变参数的方法匹配,则报错
//    private static void print(String s1, String... args) {
//
//    }
}

```
### 静态导入
- import static
- 导入类中的静态方法
    - 使用时可以直接写方法名,不写类名
- 少用*
- 如果重名需要补充类名
    

### 自动装箱和拆箱
- JDK5开始引入,简化基本类型和对象转化的写法
- Integer--int...
- 编译器的工作,不是jvm的工作
- `==`:基本类型是内容相同,对象是指针是否相同
- 基本类型没有空值,对象有null
- 基础数据类型与封装类型进行 `==`,`+`,`-`,`*`,`/`操作运算,会将封装类进行拆箱,对基础数据类型进行运算
- 谨慎使用多个非同类的数值类进行运算
```java
package notes.Java.Java_Final.suger;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-07 15:31
 */
public class testNumber {
    public static void main(String[] args) {
        Integer a1 = 1000;
        int a2 = 1000;
        Integer a3 = 2000;
        Long a4 = 2000L;
        long a5 = 2000L;
        System.out.println(a1 == a2);//拆箱再进行数值比较
        System.out.println(a3 == (a1 + a2));//拆箱再进行数值比较
        System.out.println(a4 == (a1 + a2));//拆箱再进行数值比较
        System.out.println(a5 == (a1 + a2));//拆箱再进行数值比较

        System.out.println(a3.equals(a1 + a2));//equals要求同类且内容相同
        System.out.println(a4.equals(a1 + a2));//equals要求同类且内容相同
        System.out.println(a4.equals((long)(a1 + a2)));//equals要求同类,且值相同
//        System.out.println(a3 == a4);//不同类型不能比较
    }
}

```

### 多异常并列
- 多个异常用 `|` 隔开
- 多个异常之间不能有直接/简介继承关系
### 数字增强
- 数字可以二进制赋值
    - 避免二进制计算
    - byte/short/int/long
- 数值字面量中可以使用下划线
    - 增加数字的可读性和纠错功能
    - short/int/long/float/double
    - 下划线只能出现在数字中间
```java
package notes.Java.Java_Final.suger;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-07 15:40
 */
public class testNumber2 {
    public static void main(String[] args) {
        test1();
        test2();

    }

    private static void test2() {
        long a1 = 9999999999L;
        long a2 = 9_999_999_999L;
        int a3 = 0b0111_1011_0001;//二进制0b开头
        int a4 = 02_014;//8进制,0开头
        int a5 = 123__45;//可以多个下划线
        int a6 = 0x7_B_1;//十六进制
        float a7 = 3.56_78f;//float
        double a8 = 1.3_45_67;//double

//        int b1 = 0b_123_4; _必须在数字之间
//        int b2 = 0123_4_;不能在末尾
//        int b3 = _123;不能在开头
//        int b4 = 0_ x_123;不能拆开0x
//        int b5 = 0x_51;_必须在数字之间
//        long b6 = 1000_L;_必须在数字之间
//        float b7 = 1.34f _;_不能在末尾
        
    }

    private static void test1() {
        byte a1 = (byte) 0b00100001;
        short a2 = (short) 0b001010100;
        int a3 = 0b0010101010;
        int a4 = 0b101;
        int a5 = 0B101;//B可以大小写
        long a6 = 0b1010101010010010010101010L;
        final int[] s1 = {0b101010, 0b101101, 0b1101111, 0b1111};
        System.out.println(a5);
        System.out.println(Integer.toBinaryString(a5));
    }
    
}

```
### 接口方法
- java8 接口默认方法/静态方法(都带具体实现)
- 默认方法
    - 规则
        - 以default关键字标注,其他定义和普通函数一样
        - 规则1:默认方法 不能重写 Object中的方法
        - 规则2:实现类可以继承/重写父接口默认方法
        - 规则3:接口可以继承/重写父接口的默认方法
        - 规则4:当父类和父接口都有(同名同参数) 默认方法,子类继承父类的默认方法,这样可以兼容JDK7及以前的代码
        - 规则5:子类实现了两个接口(均有同名同参数的默认方法),那么编译失败,必须在子类中重写这个default方法
- 静态方法
    - 属于本接口,不属于子类/子接口
    - 子类(子接口)没有继承该静态方法,只能通过所在的接口名来调用
-java9 私有方法
    - 解决多个默认方法/静态方法的内容重复问题
    - 私有方法属于本接口,只在本接口内使用,不属于子类/子接口
    - 子类(子接口)没有继承该私有方法,也无法调用
    - 静态私有方法可以被静态/默认方法调用,非静态私有方法被默认方法调用
    
- 接口,抽象类
    - 相同点
        - 都是抽象的,都不能被实例化,即不能被new
        - 都可以有实现方法
        - 都可以不需要继承者实现所有方法
    - 不同点(java12之前)
        - 抽象类最多只能继承一个,接口可以实现多个
        - 接口的变量默认是public static final,且必须有初值,子类不能修改,而抽象类的变量默认是default,子类可以继承修改
        - 接口没有构造函数,抽象类有构造函数
        - 接口没有main函数,抽象类可以有main函数
        - 接口有public/default/private的方法,抽象类只有public/private/protected/不写关键字的(default)的方法
### try-with-resource
- jdk7提供try-with-resource
    - 资源要求定义在try中,若已经定义在外面,则需要一个本地变量
- jdk9不在要求定义临时变量,可以直接使用外部资源变量
- 原理
    - 资源对象必须实现AutoCloseable接口,即close方法,jdk自带
```java
package notes.Java.Java_Final.suger.testTryWithResource;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-07 16:58
 */
public class test {
    public static void main(String[] args) {
        try (MyConnection connection = new MyConnection()) {
            connection.sendData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class MyConnection implements AutoCloseable {
    public void sendData() throws Exception {
        System.out.println("Send Data...");
    }
    @Override
    public void close() throws Exception {
        System.out.println("Close...");
    }
}
```

### ResourceBundle文件加载
- JDK9及以后,ResourceBundle默认以UTF-8方式加载
### var类型
- java10推出var
    - 避免信息冗余
    - 对齐了变量名
    - 更容易阅读
    - 本质上还是强类型语言,编译器负责推断类型,并写入字节码文件,因此推断后不能更改
- var的限制
    - 可以用在局部变量中,非类成员变量
    - 可以在for/for-each中
    - 声明时必须初始化
    - 不能用在方法(形式)参数和返回类型
    - 大面积滥用会使代码整体阅读性变差
    - var 只在编译时起作用,没有在字节码中引入新的内容,也没有专门的jvm指令处理var
### switch
- 支持的类型:byte/Byte,short/Short,int/Integer,char/Character,String(7.0),Enum(5.0)
- 仍不支持long/float/double
- 多分支合并
```java
package notes.Java.Java_Final.suger;

import java.util.Scanner;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-07 17:16
 */
public class testSwitch {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        Scanner scanner = new Scanner(System.in);
        int num= scanner.nextInt();
        int days = switch (num){
            case 1,3,5,7,8,10,12 -> 31;
            case 4,6,9,11 -> 30;
            default -> 28;
        };
        System.out.println(days);
    }

    private static void test1() {
        Scanner scanner = new Scanner(System.in);
        String month = scanner.next();
        int result=-1;
        switch (month) {
            case "Jan","Mar","May","July","Aug","Oct","Dec" -> result = 31;
            case "Apr","June","Sep","Nov" -> result = 30;
            case "Feb" -> result = 28;
            default -> result = -1;


        }
        System.out.println(result);
    }
}


```

## 反射 reflection
- 定义
    - 程序可以访问,检测和修改它本身状态或行为的能力,即自描述和自控制
    - 可以在运行时加载,探知,和使用编译期间完全未知的类
    - 给java插上动态语言特性的翅膀,弥补强类型语言的不足
    - java.lan.reflect包,在java2存在,java5完善
- 功能
    - 在运行中分析类的能力
    - 在运行中查看和操作对象
        - 基于反射自由创建对象
        - 反射构建出无法直接访问的类
        - set或者get到无法访问的成员变量
        - 调用不可访问的方法
    - 实现通用的数组操作代码
    - 类似函数指针的功能
- 创建对象
    1. 静态编码&编译(new)
    2. 克隆(clone()),继承Cloneable,速度快
    3. 序列化serialization,反序列化deserialization继承Serializable接口
        - ```java
            package notes.Java.Java_Final.Reflect;
            
            import java.io.*;
            
            /**
             * @Description:
             * @author: Anhlaidh
             * @date: 2020-07-07 18:05
             */
            public class testSerialization {
                public static void main(String[] args) throws IOException, ClassNotFoundException {
                    A obj1 = new A();
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
                    out.writeObject(obj1);
                    out.close();
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"));
                    A obj2 = (A) in.readObject();
                    in.close();
                    obj2.hello();
                }
            
            }
            
            class A implements Serializable {
                private static final long serialVersionUID = 1L;
            
                public void hello() {
                    System.out.println("hello from A");
            
                }
            
            }
            ```
    4. 反射
        ```java
        package notes.Java.Java_Final.Reflect;
        
        
        import java.lang.reflect.Constructor;
        import java.lang.reflect.InvocationTargetException;
        import java.lang.reflect.Method;
        
        /**
         * @Description:
         * @author: Anhlaidh
         * @date: 2020-07-07 19:32
         */
        public class testNewInstance {
            public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
                Object obj1 = Class.forName("notes.Java.Java_Final.Reflect.A").newInstance();
                Method m = Class.forName("notes.Java.Java_Final.Reflect.A").getMethod("hello");
                m.invoke(obj1);//获取方法
                A obj2 = (A) Class.forName("notes.Java.Java_Final.Reflect.A").newInstance();
                Constructor<A> constructor = A.class.getConstructor();//构造函数
                A obj3 = constructor.newInstance();
                obj3.hello();
        
            }
        
        }

        ``` 
- 内容
    1. Class:类型标识
        - JVM为每个对象都保留其类型标识信息(Runtime Type Identification)
        - 成员变量,方法,构造函数,修饰符,包,父类,父接口...
        - 成员变量Field
        - 方法Method
        - 构造函数Constructor
        
## 编译器API
- 对 .java文件即时编译
- 对字符串即时编译
- 监听在编译过程中产生的警告和错误
- 在代码中运行编译器(并非:Runtime命令调用javac命令)
### JavaCompiler
- 1.6推出
- 可用在程序文件中的Java编译器接口(代替javac.exe)
- 在程序中编译java文件,产生class文件
- run方法(继承自java.tools.Tools):较简单,可以编译java源文件,生成class文件,但不能指定输出
路径,监控错误信息,调用后就在源码所在目录生成class文件
- getTask方法:更强大的功能,可以编译java源文件,包括在内存中的java文件(字符串),生成class文件

- 编译文件:
```java
package notes.Java.Java_Final.JavaCompiler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-04 14:30
 */
public class SimpleJavaCompiler {
    public static void main(String[] args) {
//        successCompile();

        failCompile();
    }

    private static void failCompile() {
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, err, "./aaa.java");
        if (0 == result) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
            System.out.println(new String(err.toByteArray(), Charset.defaultCharset()) );
        }
    }


    private static void successCompile() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        /**
         * 第一个参数:输入流,null表示默认使用system.in
         * 第二个参数:输出流,null标识默认使用system.out
         * 第三个参数:错误流,null标识默认使用system.err
         * 第四个参数:String...需要编译的文件名
         * 返回值:0表示成功,其他错误
         */
        int result = compiler.run(null, null, null, "G:\\Coding\\src\\main\\java\\notes.Java\\Java_Final\\API\\hello1.java","G:\\Coding\\src\\main\\java\\notes.Java\\Java_Final\\API\\hello2.java");

        System.out.println(0 == result ? "Success" : "Fail");
    }
}

```
- 编译字符串
```java
package notes.Java.Java_Final.JavaCompiler;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-04 14:46
 */
public class JavaCompilerTask {
    public static void main(String[] args) {
        compilerJavaFromString();
//        System.out.println("hello world");
    }

    private static void compilerJavaFromString() {
        StringBuffer stringBuffer = new StringBuffer();
        String className = "Hello";
        stringBuffer.append("public class " + className + "{");
        stringBuffer.append(" public static void main(String[] args) {");
        stringBuffer.append(" System.out.println(\"hello world\");\n");
        stringBuffer.append("}\n");
        stringBuffer.append("}\n");


        Class<?> c = compiler(className, stringBuffer.toString());
        try {
            //生成对象
            Object obj = c.newInstance();
            Method m = c.getMethod("main", String[].class);
            m.invoke(obj, new Object[]{new String[]{}});

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static Class<?> compiler(String className, String javaCode) {
        JavaSourceFromString srcObject = new JavaSourceFromString(className, javaCode);
        System.out.println(srcObject.getCode());
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, Charset.defaultCharset());
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        //设置编译的输出目录,并包装在options中
        String flag = "-d";
        String outDir = "";
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("");
            File classpath = new File(resource.toURI());
            outDir = classpath.getAbsolutePath() + File.separator;
            System.out.println(outDir);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Iterable<String> options = Arrays.asList(flag, outDir);
        /**
         * JavaCompiler.getTask方法:以future的任务形式(多线程)来执行编译任务
         * 第一个参数:额外输出流,null表示默认使用System.err
         * 第二个参数:文件管理器,null表示编译器默认方法来报告诊断信息
         * 第三个参数:诊断监听器,null表示使用编译器默认方法来报告诊断信息
         * 第四个参数:编译器参数,null表示无参数
         * 第五个参数:需要经过annotation处理的类名,null表示没有类需要annotation
         * 第六个参数,待编译的类
         */
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector, options, null, fileObjects);
        //等待编译结束
        boolean result = task.call();
        if (result == true) {

            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            for (Diagnostic diagnostic : diagnosticCollector.getDiagnostics()) {
                System.out.println("Error on line:" + diagnostic.getLineNumber() + ";URI" + diagnostic.getSource().toString());
            }

        }
        return null;
    }

}

```
- 其中的JavaSourceFromString 类
```java
package notes.Java.Java_Final.JavaCompiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @Description: A file object used to represent source coming from a string
 * @author: Anhlaidh
 * @date: 2020-08-04 14:57
 */
public class JavaSourceFromString extends SimpleJavaFileObject {
    private String code;
    public JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }

    public String getCode() {
        return code;
    }
}

```
- notes.Java EE 的Jsp编译
- 在线编程观景
- 在线程序评判系统(OJ)
- 自动化的构建和测试工具

## 代理(Proxy):代替处理
- 代理模式
    - 外界不用直接访问目标对象,而是访问代理对象,由代理对象再调用目标对象
    - 代理对象中可以添加监控和审查处理
    - 静态代理
        - 代理对象持有目标对象的句柄
        - 所有调用目标对象的方法,都调用代理对象的方法
        - 对每个方法,需要静态编码(理解简单,但代码繁琐)
    - 动态代理
        - 对目标对象的方法每次被调用,进行动态拦截
        - 方法重名会用第一个接口的方法
- Main
```java
package notes.Java.Java_Final.Proxy.test;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-04 18:06
 */
public class MultipleInterfacesProxyTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Cook cook = new CookImpl();
        ClassLoader cl = MultipleInterfacesProxyTest.class.getClassLoader();
        ProxyHandler handler = new ProxyHandler(cook);
        //生成代理类型
        Class<?> proxyClass = Proxy.getProxyClass(cl, new Class<?>[]{Cook.class, Driver.class});
        //生成代理对象
        Object proxy = proxyClass.getConstructor(new Class[]{InvocationHandler.class}).newInstance(new Object[]{handler});
        System.out.println(Proxy.isProxyClass(proxyClass));

        Proxy p = (Proxy) proxy;
        System.out.println(Proxy.getInvocationHandler(proxy).getClass().getName());
        System.out.println("proxy类型" + proxyClass.getName());
        //代理对象都继承于java.lang.reflect.Proxy,但是获取父类是Object而不是Proxy
        Class father = proxyClass.getSuperclass();
        System.out.println("proxy的父类类型:" + father.getName());
        Class[] cs = proxy.getClass().getInterfaces();
        for (Class c : cs) {

            System.out.println("proxy的父接口类型" + c.getName());
        }
        System.out.println("===============");
        Method[] ms = proxy.getClass().getMethods();
        for (Method m : ms) {
            System.out.println("调用方法" + m.getName() + "参数为" + Arrays.deepToString(m.getParameters()));
        }
        System.out.println("===============");
        Cook c = (Cook) proxy;
        c.doWork();
        Driver d = (Driver) proxy;
        d.doWork();

    }
}

```
- Handler
```java
package notes.Java.Java_Final.Proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-04 18:03
 */
public class ProxyHandler implements InvocationHandler {
    private Cook cook;

    public ProxyHandler(Cook cook) {
        this.cook = cook;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy类型" + proxy.getClass().getName());
        System.out.println("调用方法" + method + ";参数为" + Arrays.deepToString(args));
        Object result = method.invoke(cook, args);
        return result;
    }
}

```
## AOP(Aspect Oriented Programming)
- 面向对象: 
将需求功能划分为不同的,独立,分装良好的类,并让他们通过继承和多态实现相同和不同行为
- 面向切面: 
将通用需求功能从众多类中分离出来,使得很多类共享一个行为,一旦发生变化,不必修改很多类,而只修改这个行为即可
### 面向切面编程
- 一个概念/规范,没有限定语言
- 不是取代OOP编程,而是OOP的补充,和数据库的触发器有点相似
- 主要内容
    - Aspect:配置文件,包括一些Pointcut和响应的Advice
    - JointPoint:在程序中明确定义的点,如方法调用,对类成员访问等
    - PointCut: 一组JointPoint,可以哦通过逻辑关系/通配符/正则等组合起来,定义了响应advice将要发生的地方
    - Advice:定义了在pointcut处要发生的动作,通过before/after/around来关联
    - weaving:advice代码在具体jointPoint的关联方式
    
## 注解Annotation
- JDK1.5引入
- 位于源码中(代码/注释/注解),使用其他工具进行处理的标签
- 注解用来修饰程序的元素,但不会对修饰的对象有直接的影响
- 只有通过某种配套的工具才会对注解信息进行访问和处理
- 主要用途
    - 提供信息给编译器/IDE工具
    - 可用于其他工具来产生额外的代码/配置文件等
    - 有一些注解可在程序运行时访问,增加程序动态性
- @SuppressWarning
    - 不同jdk 不同,javac -X可查看
### 自定义注解 
- 注解可以包括的类型
    - 8种基本类型
    - String
    - Class
    - enum
    - 注解类型
    - 由前面类型组成的数组

```java
package notes.Java.Java_Final.Annotation;

public @interface BugReport {
    enum Status {UNCONFIRMED,CONFIRMED,FIXED, NOTABUG};

    boolean showStopper() default true;

    String assignedTo() default "[notes]";

    Status status() default Status.UNCONFIRMED;

    String[] reportedBy();
}

```

- 注解使用的位置(@Target)
- @Retention(RetentionPolicy.RUNTIME) 编译时也存在
- 自定义注解及其使用
```java
package notes.Java.Java_Final.Annotation.Single;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: Anhlaidh 
 * @date: 2020-08-05 17:18
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        int passed = 0, failed = 0;
        String className = "notes.Java.Java_Final.Annotation.Single.Foo";
        for (Method m : Class.forName(className).getMethods()) {
            if (m.isAnnotationPresent(SingleTest.class)) {
                System.out.println(m.getName());
                SingleTest st = m.getAnnotation(SingleTest.class);
                try {
                    m.invoke(null, st.value());
                    passed++;
                } catch (Throwable throwable) {
                    System.out.printf("Test %s failed %s %n", m, throwable.getCause().getMessage());
                    failed++;
                }
            }
        }
        System.out.printf("Success: %d ,Failed: %d %n", passed, failed);
    }
}

```

### 元注解
- 修饰注解的注解
- @Target 设置目标范围
    - 限定目标注解作用于什么位置@Target({ElementType.METHOD})
    - ElementType.ANNOTATION_TYPE(注:修饰注解)
    - ElementType.CONSTRUCTOR
    - ElementType.FIELD
    - ElementType.LOCAL_VARIABLE
    - ElementType.METHOD
    - ElementType.PACKAGE
    - ElementType.PARAMETER
    - ElementType.TYPE(注:任何类型,即上面的类型都可以修饰)
    
    
- @Retention 设置保持性
     - 修饰其他注解的存在范围
     - Retention.SOURCE 注解仅存在源码,不在class文件
     - Retention.CLASS(默认的注解保留策略) 注解存在于.class文件,不能被JVM加载
     - Retention.RUNTIME这种策略下,注解可以被JVM运行时访问到,通常情况下,可以结合反射来做一些事情
     
- @Inherited 注解继承
    - 让一个类和他的子类都包含某个注解
    - 普通的注解没有继承功能
- @Repeatable 此注解可以重复修饰
    - 自jdk1.8引入
    - 表示被修饰的注解可以重复应用标注
    - 需要定义注解和容器注解
- @Document 文档
    - 指明这个注解可以被javadoc工具解析,形成帮助文档
- RUNTIME注解调用路线
    1. Java为注解产生一个代理类
    2. 这个代理类包括一个AnnotationInvocationHandler成员变量
    3. AnnotationInvocationHandler有个Map的成员变量,用来存储所有的注解的属性赋值
    4. 在程序中,调用注解接口的方法,将会被代理类接管,然后根据方法名字,到Map里拿到对应的Value并返回

## 嵌套类 Nested classes
- 静态嵌套类: Static nested classes,即前面有static修饰符
    - 层级和包围类(enclosing class)的成员变量/方法一样
    - 静态嵌套类可以定义静态和非静态成员
    - 第三方需要通过外部包围类才可以访问到静态嵌套类
        - `Outer.Inner obj = new Outer.Inner();`
- 非静态嵌套类: Non-static nested classes ,又名内部类,Inner classes
    - 普通内部类(成员内部类)
        - 非static的类,定义在某个类的成员变量位置
        - 定义后在类里面均可以使用
        - 在第三方类中,需要先创建外部包围类实例,才能创建内部类的实例,不允许单独的普通内部类对象存在
            - `Outer.Inner obj = Outer.new Inner();`
    - 局部内部类(Local classes)
        - 定义在代码块中的非静态的类,如方法,for循环,if语句等
        - 定义后,即可创建对象使用
        - 只能活在这个代码块中,代码块结束后,外界无法使用该类
    - 匿名内部类(Anonymous class)
        - 没有类名的内部类,必须继承一个父类/实现一个父接口
        - 在实例化以后,迅速转型为父类/父接口
        - 这种类型的对象,只能new一个对象,之后以对象名字操作
        - 可在普通语句和成员变量赋值时使用内部类
- 为什么需要嵌套类
    - 不同的访问权限要求,更细粒度的访问控制
    - 简介,避免过多的类定义
    - 语言设计过于复杂,较难学习和使用
    
    | |位置 |名字(编译后的class文件)| 作用范围 | 基本信息 |嵌套类内部的内容|可访问的外部包围类的内容|和外部类的关系
    |---|---|---|---|---|---|---|---|
    | 匿名内部类|成员变量或者成员方法内| 外部类名+$+数字编号|跟随被复制变量的作用范围,<br>外界无法访问|没有类名,没有构造函数<br>没有static,private/default/protected/public修饰|不能带静态成员|访问外部的所有成员|在外部类对象内部
    | 局部内部类|成员方法内| 外部类名+$+序号+内部类名|所在的方法内<br>外界无法访问|有类名,有构造函数<br>没有static,private/default/protected/public修饰|不能带静态成员,除了常量|访问外部的所有成员|在外部类的对象内部
    | 普通内部类|成员变量| 外部类名+$+内部类名|包围类内可以访问,<br>外界可以访问|有类名,有构造函数<br>没有static,private/default/protected/public修饰|不能带静态成员,除了常量|访问外部的所有成员|外界可以new,<br>但是必须依附于一个外部包围类对象
    | 静态嵌套类|成员变量| 外部类名+$+内部类名|包围类内部可以访问,<br>外界可以访问|有类名,有构造函数<br>有static,private/default/protected/public修饰|可以定义静态成员变量和方法|访问外部的所有静态成员|可以new,可独立进行工作
- 变量遮蔽:Shadowing
    - 嵌套类变量和外部包围类的变量重名
        - 以离得近作为优先原则
        - 优先级高的变量回遮蔽优先级低的变量
        - 外部包围类.this.变量名,可以访问到外部包围类的成员变量
        - 静态嵌套类不能访问非静态变量
        - Java7及以前,匿名内部类和局部内部类只能访问外部包围类的final成员变量
        - Java8及以后,匿名内部类和局部内部类可以访问外部包围类的final成员变量和
        事实意义上的final变量(effectively final,一个变量定值后,再也没有改过值)
## Lambda表达式
- 参数,箭头,一个表达式
- 参数,箭头,{多个语句}
- 类似于匿名方法,一个没有名字的方法
- 参数,箭头,表达式语句
- 可以忽略写参数类型
- 坚决不声明返回值类型
- 没有public/protected/private/static/final等修饰符
- 单句表达式,将直接返回值,不用大括号
- 带return语句,算多句,必须用大括号
### 函数式接口
- 是一个接口,符合Java接口的定义
- 只包含一个抽象方法的接口
- 可以包含其他的default方法,static方法,private方法
- 由于只有一个未实现的方法,所以Lambda表达式可以自动填上这个尚未实现的方法
- 采用Lambda表达式,可以自动创建出一个(伪)嵌套类的对象(没有实际嵌套类class文件产生),然后使用,
比真正嵌套类更加轻量,更加简洁高效

```java
package notes.Java.Java_Final.Lambda;
@FunctionalInterface
//标记为函数式接口
public interface StringChecker {
    public boolean test(String s);
}

```
```java
package notes.Java.Java_Final.Lambda;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-06 21:38
 */
public class Main {
    public static void main(String[] args) {
        String[] s = new String[]{"aaa", "bbbb", "cccccc"};
        StringChecker eventLength = s1 -> {
            if (s1.length() % 2 == 0) {
                return true;
            }
            return false;
        };

        for (String p : s) {
            if (eventLength.test(p)) {
                System.out.println(p);

            }
        }
    }

}

```

### 系统自带的函数式接口
接口|参数|返回值|实例
---|---|---|---
Predicate<T>|T|Boolean|接收一个参数,返回一个boolean
Consumer<T>|T|void|接受一个参数,无返回
Function<T,R>|T|R|接受一个参数,返回一个值
Supplier<T>|None|T|数据工厂
### 方法引用
- Class::staticMethod
```java
package notes.Java.Java_Final.Lambda;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-06 22:26
 */
public class method {
    public static void main(String[] args) {
        double a = -3.5;
        double b = worker(Math::abs, a);
        System.out.println(b);
        double c = worker(Math::floor, a);
        System.out.println(c);
        double d = worker((num -> (num % 10)), a);
        System.out.println(d);

    }

    public static double worker(NumFunction nf, double num) {
        return nf.calculate(num);
    }
}

```
- Class::instanceMethod
    - 第一个参数将变成方法的执行体
    - String::compareToIgnoreCase等价于(x,y)->x.compareToIgnoreCase(y)
    - ```
          String[] planets = new String[]{"DDD","aaa", "bbb", "ccc"};
          Arrays.sort(planets, String::compareToIgnoreCase);
          System.out.println(Arrays.toString(planets));
      ```
- object::instanceMethod
    - 支持this::instanceMethod
    - 支持super::instanceMethod
- Class::new,调用某类构造函数,支持单个对象构建
- Class[]::new,调用某类构造函数,支持数组对象构建
- 应用
```java
package notes.Java.Java_Final.Lambda;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-07 15:06
 */
public class testIterable {
    public static void main(String[] args) {
        String[] p = new String[]{"aaa", "bbb", "ccc"};
        ArrayList<String> pList = new ArrayList<String>(Arrays.asList(p));
        for (String s : pList) {
            System.out.println(s);
        }
        pList.forEach(System.out::println);

    }
}

```

## 流 stream
- sequence of elements:一个流对外提供一个接口,可以访问到一串特定的数据,流不存储元素,但是可以根据需要进行计算转化
- source:数据来源,如数据结构,数组,文件等
- aggregate operation:聚合操作,流支持像SQL操作或者其他函数式语言的操作
    如filter/map/reduce/find/match/sorted等
- Stream流
    - pipelining:很多流操作也是返回一个流
    - Internal Iteration:流操作进行迭代,用户感知不到循环遍历
### 流的工作流程
1. 流的创建
    - Collection接口的stream方法
    - Arrays.stream(数组)
    - Stream.of()
    - empty() 空流
    - 
2. 流的转换,将流转换为其他流的中间操作,可包括多个步骤(惰性操作)
3. 流的计算结果,这个操作会强制执行之前的惰性操作,这个步骤以后,流就再也不用了