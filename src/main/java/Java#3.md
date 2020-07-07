# Java

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
package Java.Java_Final.suger;

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
package Java.Java_Final.suger;

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
package Java.Java_Final.suger;

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
package Java.Java_Final.suger;

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
package Java.Java_Final.suger.testTryWithResource;

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
package Java.Java_Final.suger;

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
            package Java.Java_Final.Reflect;
            
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
        package Java.Java_Final.Reflect;
        
        
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
                Object obj1 = Class.forName("Java.Java_Final.Reflect.A").newInstance();
                Method m = Class.forName("Java.Java_Final.Reflect.A").getMethod("hello");
                m.invoke(obj1);//获取方法
                A obj2 = (A) Class.forName("Java.Java_Final.Reflect.A").newInstance();
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
        