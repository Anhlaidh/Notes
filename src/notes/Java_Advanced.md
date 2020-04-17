# Java_Advanced

## Maven

## Junit

### 测试

- 单元测试：对软件中最小可测试单元进行检查和验证，通常是一个函数/方法（属于白盒测试）
    - 集成测试： 将多个单元相互作用，形成一个整体，对整体协调性进行测试

- 白盒测试： 全面了解程序内部逻辑结构，对所有的逻辑路径都进行测试，一般由程序员完成
    - 黑盒测试：又名功能测试，将程序看作不可打开的盒子，一般由独立使用者完成
    
- 自动测试：用程序批量、反复的测试程序，并可自动检查程序结果是否满足预定的要求
    - 手动测试：手动执行程序，手动输入所需要的参数，手动检查程序结果是否满足预定的要求

- 回归测试：修改旧代码后，重新进行测试以确认修改没有引入新的错误或导致其他代码产生错误

- 深入学习：软件测试-基于问题驱动模式 朱少民

#### Junit

- import static     导入该包内的所有静态方法，使用时可不加类名

- Triangle

```java
package JavaLearning_Advanced.Maven;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 13:42
 */
public class Triangle {
    public boolean judgeEdges(int a, int b, int c) {
        boolean result = true;
        //边长非负性
        if (a <= 0 || b <= 0 || c <= 0) {
            return false;
        }
        //两边和大于第三边
        if (a+b<=c) return false;
        if (b+c<=a) return false;
        if (c+a<=b) return false;

        return true;
    }
}

``` 
- testTriangle
```java
import JavaLearning_Advanced.Maven.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 13:44
 */
public class testTriangle {
    @Test
    public void test() {
        assertEquals(false,new Triangle().judgeEdges(1,2,3));
    }
}

```
- 深入学习：Junit实战（第二版），PetarTahchiey

## Java字符编码
- 尽可能使用UTF-8
- 读取写入编码保持一致

## 国际化

- Locale 方法
    - getAvailableLocales()获取所有可用的locale
    - getDefault()返回默认的Locale
    
- native2ascii.exe

- ResourceBundle
    - 根据Locale要求，加载语言文件
    - 存储语言集合中的K-V对
    - getString(String key) 返回对应的value
```java
package JavaLearning_Advanced.International;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 14:34
 */
public class HelloWorld {
    public static void main(String[] args) {
        //取得系统默认的国家/语言环境
        Locale myLocale = Locale.getDefault();
        System.out.println(myLocale);//zh_CN
        //根据指定语言，国家环境，加载资源文件
        ResourceBundle bundle = ResourceBundle.getBundle("message", myLocale);
        //从资源文件中获取信息
        System.out.println(bundle.getString("hello"));

    }
}
```

## 高级字符编码

### 正则表达式

- 学习正则表达式：
精通正则表达式（第三版） Jefferry E.F.Friedl

- java.util.regex包
    - Pattern 正则表达式的编译表示
        - compile编译一个正则表达式喂Pattern对象
        - matcher用Pattern对象匹配一个字符串，返回匹配结果
    - Matcher
        - IndexMethod(位置方法) //start(),start(int group),end(),end(group)
        - StudyMethod(查找方法) //lookingAt(),find(),find(int start),matches() 
        - Replacement(替换方法) //replaceAll(String replacement)

Matcher
```java
package JavaLearning_Advanced.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 19:21
 */
public class MatcherDemo {
    private static final String REGEX = "\\bdog\\b";//\b表示边界
    private static final String INPUT = "dog dog dog doggie dogg";

    public static void main(String[] args) {
        //检查字符串里有多少个dog
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(INPUT);
        int count = 0;
        while (matcher.find()) {
            count++;
            System.out.println("Match number" + count);
            System.out.println("start()" + matcher.start());
            System.out.println("end()" + matcher.end());
//        String f = "fooooooooooooo";
//        matcher.lookingAt();//不完全匹配,匹配foo为true
//        matcher.find();//完全匹配，匹配foo为false
        }
    }
}

```
Replace
```java
package JavaLearning_Advanced.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 19:39
 */
public class ReplaceDemo {
    public static void Replace_1() {
        String REGEX = "a*b";//*表示限定前面的a可以有0个或者多个
        String INPUT = "aavfooabfooabafoobcdd";
        String REPLACE = "-";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(INPUT);
        StringBuffer stringBuffer = new StringBuffer();
        // 全部替换
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, REPLACE);
        }
        //最后将尾巴字符串附加上
        matcher.appendTail(stringBuffer);
        System.out.println(stringBuffer.toString());

    }

    public static void Replace_2() {
        String REGEX = "dog";//*表示限定前面的a可以有0个或者多个
        String INPUT = "The dog says meow.All dogs say meow";
        String REPLACE = "cat";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(INPUT);
        INPUT = matcher.replaceAll(REPLACE);
        System.out.println(INPUT);

    }
}

```

- OJ
```java
package JavaLearning_Advanced.regex;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/23 0023 19:55
 */
public class String2inputStream {
    public static void main(String[] args) {
        //构造字符串列表
        List<String> names = new LinkedList<>();
        names.add("xiaohong");
        names.add("xiaoming");
        names.add("Daming");
        names.add("xiaohei");
        //合并为一个字符串，以逗号相连
        String nameStr = String.join(",", names);
        //将字符串作为默认输入流
        InputStream in = IOUtils.toInputStream(nameStr, Charsets.toCharset("UTF-8"));
        //重置系统的输入流
        System.setIn(in);
        //模拟键盘输入，这也是OJ平台测试用例输入的原理
        //此处也可以换成一个文件的输入流
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }
}
```

## xml

- 常规语法
    - 任何的起始标签都必须有一个结束标签
    - 简化写法，例如，<name></name>可以写为<name/>
    - 大小写敏感，name和Name不一样
    - 每个文件都要有一个根元素
    - 标签必须按合适的顺序进行嵌套，不可错位
    - 所有的特性都必须有值，且在值的周围加上引号
    - 需要转义字符，如"<"需要用&lt;代替
        - |转义 | 符号 | 意思 |
          | --- | --- | --- |
          |\&lt; | < | 小于|   
          |\&gt; | \> | 大于 |
          |\&amp;|&|和号|
          |\&apos;|'|单引号|
          |\&quot;|"|双引号|
    - 注释：&lt;!--  内容  -->

### xml解析

未完成


## JSON 

- 概念
    - JavaScript Object Notation ，JS对象表示法
    - 是一种轻量级的数据交换格式
    - 类似XML，更小、更快、更易解析
    - 最早用于JavaScript中，容易解析，最后推广到全语言
    - 尽管使用JavaScript语法，但是独立于编程语言
- 用途
    - JSON生成
    - JSON解析
    - JSON校验
    - 和JavaBean对象进行互解析
        - 具有一个无参的构造函数
        - 可以包括多个属性，所有属性都是private
        - 每个属性都有对应的Getter/Setter方法
        - JavaBean 用于封装数据，有可称为POJO(Plain Old Java Object)

```java
package JavaLearning_Advanced.Json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/26 0026 21:42
 */
public class testJackson {
    public static void main(String[] args) throws IOException {
        testJsonObject();
        System.out.println("分割线=====================================================");
        testJsonFile();

    }

    private static void testJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //从json文件中加载，并重构为java对象
        File json = new File("temp/json/books.json");
        List<Book> books = objectMapper.readValue(json, new TypeReference<List<Book>>() {
        });
        for (Book book : books) {
            System.out.println(book.getAuthor());
            System.out.println(book.getTitle());
        }

    }

    private static void testJsonObject() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //构造对象
        Person person = new Person();
        person.setName("TOM");
        person.setAge(20);
        person.setScores(Arrays.asList(60, 70, 80));
        //将对象解析为json字符串
        String jsonStr = objectMapper.writeValueAsString(person);
        System.out.println(jsonStr);
        //json字符串重构对象
        Person p2 = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(p2.getName());
        System.out.println(p2.getAge());
        System.out.println(p2.getScores());
        //从json字符串重构JsonNode对象
        JsonNode node = objectMapper.readTree(jsonStr);
        System.out.println(node.get("name").asText());
        System.out.println(node.get("age").asText());
        System.out.println(node.get("scores"));

    }
}
```
```json
[
  {
    "category": "COOKING",
    "title": "Everyday Italian",
    "author": "Giada De Laurentiis",
    "year": "2005",
    "price": 30
  },
  {
    "category": "CHILDREN",
    "title": "Harry Potter",
    "author": "J K Rowling",
    "year": "2005",
    "price": 29
  },
  {
    "category": "WEB",
    "title": "Learning XML",
    "author": "Erik T.Ray",
    "year": "2003",
    "price": 39
  }
]
```
        
- 总结
    - JSON是一种独立于编程语言的、轻量的、数据交换格式
    - 有多种第三方库辅助我们进行JSON生成和解析
    - JSON会丢失顺序性
    
## 图片
    - imageIO
    - 验证码
```java
package JavaLearning_Advanced.picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/28 0028 19:56
 */
public class ValidateCode {
    static char[] codeSequence= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','2','3','4','5','6','7','8','9'};
    static int charNum = codeSequence.length;
    public static void main(String[] args) throws IOException {
        generateCode("./temp/validateCode/code.jpg");
    }

    private static void generateCode(String filePath) throws IOException {
        //首先定义验证码图片库
        int width = 140;//验证码图片的宽度
        int height = 40;//验证码图片的高度
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //定义图片上的图形和干扰线
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.GRAY);//将图像填充为浅灰色
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);//画边框
        graphics2D.drawRect(0, 0, width - 1, height - 1);
        //随机产生16条灰色干扰线
        graphics2D.setColor(Color.darkGray);
        //创建一个随机数生成器类，用于随机产生干扰线
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(50);
            int y1 = random.nextInt(50);
            graphics2D.drawLine(x, y, x1, y1);

        }
        //计算字的位置坐标
        int codeCount = 4;//字的个数
        int fontHeight ;//字体高度
        int codeX;//第一个字符的x坐标，因为后面的字符坐标依次递增，所以他们的x轴值是codeX+i的值
        int codeY;//验证字符的y坐标，因为并排所以值一样
        //width-4除去左右多余的位置，使验证码更加集中显示，减的越多越集中
        //codeCount+1//等比分配显示的宽度，包括左右两边的空格
        codeX = (width - 4) / (codeCount + 1);//第一个字母的起始位置
        fontHeight = height - 10;//height-10高度中间区域显示验证码
        codeY = height - 7;
        //创建字体，字体的大小应该根据图片的高度来定
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        graphics2D.setFont(font);
        //随机产生codeCount数字的验证码
        for (int i = 0; i < codeCount; i++) {
            //每次随机拿一个字母，赋予随机的颜色
            String strRand = String.valueOf(codeSequence[random.nextInt(charNum)]);
            int red = random.nextInt(255);
            int blue = random.nextInt(255);
            int green = random.nextInt(255);
            graphics2D.setColor(new Color(red, green, blue));
            //把字放到图片上
            graphics2D.drawString(strRand, (i + 1) * codeX, codeY);
        }
        ImageIO.write(bufferedImage, "jpg", new File(filePath));

    }
}

```
## 多进程和多线程

### 概念

#### 多进程

- 当前的操作系统都是多任务OS
- 每个独立执行的任务就是一个进程
- OS将时间划分为多个时间片（时间很短）
- 每个时间片内将CPU分配给某一个任务，时间片结束，CPU自动回收，再分配给另外任务。从外部看，所有任务都是同时在执行。
但是在CPU上，任务是按照串行依次运行（单核CPU ）。如果多核，多个进程任务可以并行。但是单个核上，多进程只能串行执行
- 多进程的优点
    - 可以同时运行多个任务
    - 程序因IO堵塞时，可以释放CPU，让CPU为其他程序服务
    - 当系统有多个CPU时，可以为多个程序同时服务
        - 我们的CPU不再提高频率，而是提高核数
        - 多核和并行程序才是提高程序性能的唯一办法
- 多进程的缺点
    - 太笨重，不好管理
    - 太笨重，不好切换
    
#### 多线程

- 一个程序可以包括多个子任务，可串/并行
- 每个子任务可以称为一个线程
- 如果一个子任务阻塞，程序可以将CPU调度另外一个子任务进行工作，这样CPU还是保留在本程序中，而不是被调度到别的程序（进程）去，
这样，提高本程序获得CPU时间和利用率

##### 启动

- start方法，会自动以新进程调用run方法
- 直接调用run方法，会编程串行执行
- 同一个线程，多次start会报错，只执行第一次start方法
- 多个线程启动，其启动的先后顺序是随机的
- 线程无需关闭，只要其run方法执行结束后，自动关闭
- main函数（线程）可能早于新线程结束，整个程序并不终止
- 整个程序终止是等所有的线程都终止（包括main函数线程）


####多线程实现的对比

- Thread占据了父类名额，不如Runnable方便
- Thread类实现Runnable
- Runnable启动时需要Thread类的支持
- Runnable更容易实现多线程中资源共享

结论：建议实现Runnable接口来完成多线程

##### 规则
规则一：
1.调用run方法来启动run方法，将会是串行运行
2. 调用start方法，来启动run方法，将会是并行运行（多线程运行）
规则二：
1. main线程可能早于子线程结束
2. main线程和子线程都结束了，整个程序才算终止
规则三：
1.实现Runnable的对象必须包装在Thread类里面，才可以启
2. 不能直接对Runnable对象进行start方法
规则四：
1. 一个线程对象不能多次start，多次start将报异常
2. 多个线程对象都start后，哪一个先执行，完全由JVM/操作系统来主导，程序员无法指定

一：
```java
package JavaLearning_Advanced.thread.rules;

/**
 * @Description:
 *  规则一：
 *  1.调用run方法来启动run方法，将会是串行运行
 *  2. 调用start方法，来启动run方法，将会是并行运行（多线程运行）
 *
 * @author: Anhlaidh
 * @date: 2020/3/28 0028 22:59
 */

public class first {
    public static void main(String[] args) throws InterruptedException {
        new TestThread0().run();
        while (true) {
            System.out.println("main");
            Thread.sleep(10);
        }
    }

}

class TestThread0 extends Thread{
    @Override
    public void run() {
        while (true) {
            System.out.println("testThread0");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

```
二：
```java
package JavaLearning_Advanced.thread.rules;

/**
 * @Description:
 * 规则二：
 * 1. main线程可能早于子线程结束
 * 2. main线程和子线程都结束了，整个程序才算终止
 * @author: Anhlaidh
 * @date: 2020/3/28 0028 23:06
 */
public class second {
    public static void main(String[] args) throws InterruptedException {
        new TestThread1().start();
//        while (true) {
//            System.out.println("main");
//            Thread.sleep(10);
//        }
    }

}

class TestThread1 extends Thread{
    @Override
    public void run() {
        while (true) {
            System.out.println("testThread1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

```
三:
```java
package JavaLearning_Advanced.thread.rules;

/**
 * @Description:
 * 规则三：
 * 1.实现Runnable的对象必须包装在Thread类里面，才可以启动
 * 2. 不能直接对Runnable对象进行start方法
 * @author: Anhlaidh
 * @date: 2020/3/28 0028 23:33
 */
public class third {
    public static void main(String[] args) throws InterruptedException {
//        new TestThread2().start();
        //runnable对象必须放在一个Thread类中才能运行
        TestThread2 tt = new TestThread2();
        Thread thread = new Thread(tt);
        thread.start();
        while (true) {
            System.out.println("main");
            Thread.sleep(1000);
//        }
        }

    }
}

class TestThread2 implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName());
            //输出当前线程名
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```
四：
```java
package JavaLearning_Advanced.thread.rules;

/**
 * @Description:
 * 规则四：
 * 1. 一个线程对象不能多次start，多次start将报异常
 * 2. 多个线程对象都start后，哪一个先执行，完全由JVM/操作系统来主导，程序员无法指定
 * @author: Anhlaidh
 * @date: 2020/3/28 0028 23:39
 */
public class Fourth {
    public static void main(String[] args) {
        TestThread4 t1 = new TestThread4();
        t1.start();
//        t1.start();
        TestThread4 t2 = new TestThread4();
        t2.start();
    }
}

class TestThread4 extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName()+"is running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

```

#### 多进程和多线程的对比

- 线程共享数据
- 线程通讯更高效
- 线程更轻量级，更容易切换
- 多个线程更容易管理


### 多线程信息共享

- 线程类
    - 通过继承Thread或实现Runnable
    - 通过start方法，调用run方法，run方法工作
    - 线程run结束后，线程退出
- 粗粒度：子线程与子线程之间、和main线程之间缺乏交流
- 细粒度：线程之间有信息交流通讯
    - 通过共享变量达到信息共享
    - Jdk原生库不支持发送消息
- 通过共享变量在多个线程中共享消息
    - static变量
    - 同一个Runnable类的成员变量

test1:继承thread，static修饰的变量可以共享，但是会造成数据重复
```java
package JavaLearning_Advanced.thread.message;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 20:23
 */
public class ThreadDemo0 {
    public static void main(String[] args) {
        new TestThread0().start();
        new TestThread0().start();
        new TestThread0().start();
        new TestThread0().start();
    }

    private static class TestThread0 extends Thread {
//        private int tickets = 100; 每个线程卖100张，没有共享
        private static int tickets = 100;  //static变量是共享的，所有的线程共享
        @Override
        public void run() {
            while (tickets>0) {
                System.out.println(Thread.currentThread().getName() + " is selling tickets" + tickets);
                tickets = tickets - 1;

            }
        }
    }
}

```
- 多线程信息共享问题
    - 工作缓存副本
    - 关键步骤缺乏加锁限制
- i++，并非原子性操作
    - 读取主存i（正本）到工作缓存（副本）中
    - 每个CPU执行（副本）i+1操作
    - CPU将结果写入到缓存（副本）中
    - 数据从工作缓存（副本）刷到主存（正本）中
- 变量副本问题的解决办法
    - 采用volatile关键字修饰变量
    - 保证不同线程对共享变量操作时的可见性

```java
package JavaLearning_Advanced.thread.message;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 20:55
 */
public class ThreadDemo2 {
    public static void main(String[] args) throws InterruptedException {
        TestTread3 testTread3 = new TestTread3();
        testTread3.start();

        Thread.sleep(1000);
        testTread3.flag = false;
        System.out.println("main thread is exiting");

    }

    private static class TestTread3 extends Thread {
        //        boolean flag = true;//子线程不会停止
        volatile boolean flag = true;//用volatile修饰的变量可以及时在各线程里面通知
        @Override
        public void run() {
            int i = 0;
            while (flag) {
                i++;
            }
            System.out.println("test thread is exiting");

        }
    }
}

```

- 关键步骤加锁限制
    - 互斥：某一个线程运行一个代码段（关键区），其他线程不能同时运行这个代码段
    - 同步：多个线程的运行，必须按照某一种规定的先后顺序来运行
    - 互斥是同步的一种特例
- 互斥的关键字是synchronized
    - synchronized代码块/函数，只能一个线程进入
    - synchronized能加大性能负担，但是使用简便

```java
package JavaLearning_Advanced.thread.message;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 21:16
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        TestThread3 testThread3 = new TestThread3();
        new Thread(testThread3).start();
        new Thread(testThread3).start();
        new Thread(testThread3).start();
        new Thread(testThread3).start();

    }

    private static class TestThread3 implements Runnable {
        private volatile int tickets = 100;

        @Override
        public void run() {
            while (tickets>0) {
                sale();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }

        private synchronized void sale() {
            System.out.println(Thread.currentThread().getName() + " sale " + tickets--);
        }
    }
}

```

### 多线程管理

- 线程状态
    - NEW 刚创建（new）
    - RUNNABLE就绪态（start）
    - RUNNING运行中（run）
    - BLOCK阻塞（sleep）
    - TERMINATED 结束
- Thread的部分API已经废弃
    - 暂停和恢复：suspend/resume
    - 消亡：stop/destroy
- 线程阻塞/和唤醒
    - sleep，时间一到，自己就会醒来
    - wait/notify/notifyAll，等待，需要别人来唤醒
    - join，等待另一个线程结束
    - interrupt，向另外一个线程发送中断信号，该线程收到信号，会触发
    Interrupted Exception（可接触阻塞），并进入下一步处理
- 线程类
- 主动interrupt
- 被动interrupt

- 多线程死锁
    - 每个线程相互持有别人需要的锁（哲学家吃面问题）
    - 预防死锁，对资源进行等级排序
```java
package JavaLearning_Advanced.thread.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/4/7 0007 22:05
 */
public class DeadLock {
    public static Integer r1 = 1;
    public static Integer r2 = 2;
    public static void main(String[] args) {
        Thread1 t1 = new Thread1();
        t1.start();
        Thread2 t2 = new Thread2();
        t2.start();

    }

}

class Thread1 extends Thread {
    @Override
    public void run() {
        //先要r1再要r2
        synchronized (DeadLock.r1) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DeadLock.r2) {
                System.out.println("Thread1 is running");
            }
        }
    }
}
class Thread2 extends Thread {
    @Override
    public void run() {
        //先要r2再要r1
        synchronized (DeadLock.r2) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DeadLock.r1) {
                System.out.println("Thread2 is running");
            }
        }
    }
}

```    

- 守护（后台）线程
    - 普通线程的结束，是run方法运行结束
    - 守护线程的结束，是run方法运行结束，或main函数结束
    - 守护线程永远不要访问资源，如文件或数据库等
    
```java
package JavaLearning_Advanced.thread.Interrupt;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/4/7 0007 22:19
 */
public class protect {
    public static void main(String[] args) throws InterruptedException {
        Thread3 t = new Thread3();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
        System.out.println("main thread is exiting");

    }
}

class Thread3 extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("running");
        }
    }
}

```
    
- 线程查看工具jvisualvm

### 并发框架
- 业务：任务多，数据量大

- 串行 并行
    - 串行编程简单，并行编程困难
    - 单个计算核频率下降，计算核数增多，整体性能变高
- 并行困难（任务分配和执行过程高度耦合）
    - 如何控制粒度，切割任务
    - 如何分配任务给线程，监督线程执行过程
### 并行计算
- 并行模式
    - 主从模式(Master-Slave)
    - Worker模式(Worker-Worker) 
- Java并发编程
    - Thread/Runnable/Thread组管理
    - Executor(重点)
    - Fork-Join框架
    
### 线程组管理
- 线程组ThreadGroup
    - 线程的集合
    - 树形结构,大线程可以包括小线程组
    - 可以通过enumerate方法遍历组内的线程,执行操作
    - 能够有效管理多个线程,但是管理效率低
    - 任务分配和执行过程高度耦合
    - 重复创建线程,关闭线程操作,无法重用线程
    
### Executor*
- 从jdk5开始提供Executor FrameWork
    - 分离任务的创建和执行者的创建
    - 线程重复利用(new线程代价很大)
- 理解共享线程池的概念
    - 预设好的多个Thread,可弹性增加
    - 多次执行很多很小的任务
    - 任务创建和执行过程解耦
    - 程序员无需关心线程池执行任务过程

Main:
```java
package JavaLearning_Advanced.thread.Executor.example2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-04-08 22:15
 */
public class SumTest {
    public static void main(String[] args) {
        //执行线程池
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        List<Future<Integer>> resultList = new ArrayList<>();
        //统计1-1000总和,分成10个任务计算,提交任务
        for (int i = 0; i < 10; i++) {
            SumTask calculator = new SumTask(i*100+1,(i+1)*100);
            Future<Integer> result = executor.submit(calculator);
            resultList.add(result);
        }
        //每隔50毫秒,轮询等待10个任务结束
        do {
            System.out.printf("Main:已经完成了多少个任务:%d\n", executor.getCompletedTaskCount());
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> result = resultList.get(i);
                System.out.printf("Main Task %d:%s\n", i, result.isDone());
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (executor.getCompletedTaskCount() < resultList.size());
        //所有任务都已经结束,综合计算结果
        int total = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            Integer sum = null;
            try {
                sum = result.get();
                total = total + sum;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("1-1000的总和" + total);
    }
}

```
SumTask:
```java
package JavaLearning_Advanced.thread.Executor.example2;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-04-08 22:17
 */
public class SumTask implements Callable {
    private int startNumber;
    private int endNumber;

    public SumTask(int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            sum = sum + i;
        }
        Thread.sleep(new Random().nextInt(1000));
        System.out.printf("%s:%d", Thread.currentThread().getName(), sum);
        return sum;
    }
}

```
    
### Fork-Join
- 关键类
    - ForkJoinPool 任务池
    - RecursiveAction
    - RecursiveTask
Main:
```java
package JavaLearning_Advanced.thread.Fork_Join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-04-09 13:37
 */
public class SumTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建执行线程池
        ForkJoinPool pool = new ForkJoinPool(8);
        //创建任务
        SumTask task = new SumTask(1,1000000);
        //提交任务
        ForkJoinTask<Long> result = pool.submit(task);
        //等待结果
        do {
            System.out.printf("Main:Thread Count:%d\n", pool.getActiveThreadCount());
            System.out.printf("Main:Parallelism:%d\n", pool.getParallelism());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        //输出结果
        System.out.println(result.get().toString());

    }
}

```
SumTask
```java
package JavaLearning_Advanced.thread.Fork_Join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-04-09 13:37
 */
public class SumTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建执行线程池
        ForkJoinPool pool = new ForkJoinPool(8);
        //创建任务
        SumTask task = new SumTask(1,1000000);
        //提交任务
        ForkJoinTask<Long> result = pool.submit(task);
        //等待结果
        do {
            System.out.printf("Main:Thread Count:%d\n", pool.getActiveThreadCount());
            System.out.printf("Main:Parallelism:%d\n", pool.getParallelism());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        //输出结果
        System.out.println(result.get().toString());

    }
}

```
#### 主要类
- ExecutorService,ThreadPoolExecutor,Future
    - Executors.newCachedThreadPool/newFixedThreadPool创建线程池
    - ExecutorService线程池服务
    - Callable 具体的逻辑对象(线程类)
    - Future返回结果

### 并发数据结构

- 阻塞式集合:当集合为空或者满时,等待
- 非阻塞式集合:当集合为空或者满时,不等待,返回null或异常


-List
    - Vector 同步安全,写多读少
    - ArrayList 不安全
    - Collections.synchronizedList(List list) 基于synchronized,效率差
    - CopyOnWriteArrayList 读多写少,基于复制机制,非阻塞
- Set
    - HashSet不安全
    - Collections.synchronizedSet(Set set)基于synchronized,效率差
    - CopyOnWriteArraySet (基于CopyOnWriteArrayList实现),读多写少,非阻塞
- Map
    - Hashtable 同步安全,写多读少
    - HashMap 不安全
    - Collections.synchronizedMap(Map map)基于synchronized,效率差
    - ConcurrentHashMap,读多写少,非阻塞
- Queue&Deque
    - ConcurrentLinkedQueue非阻塞
    - ArrayBlockingQueue/LinkedBlockingQueue阻塞
    
    
## Java网络编程

### 网络基础知识

- 网络是当前信息技术的第一推动力
- 每个计算设备上都有若干个网卡
- 每个网卡上有（全球唯一）单独的硬件地址，MAC地址

- ip地址
    - IPV4 192.169.0.1每段0-255
    - IPV6 128bit长，8段，每段4个16进制数
    - ipconfig ifconfig

- port:端口 0~65535
    - 0~1023OS已经占用了，80是web，23是telnet
    - 1024~65535，一般程序可使用（谨防冲突）
- 两台机器通讯就是在IP+port上进行的
    - netstat -an
    
- 保留ip：127.0.0.1 本机
- 公网（万维网/互联网）和内网（局域网）
    - 网络上分层的
    - 最外层的是外网/互联网
    - 底下每层都是内网
    - ip地址可以在每个层次的网重用
    - tracert 看当前及其和目标机器的访问中继
- 通讯协议TCP UDP
    - TCP(Transmission Control Protocol)
        - 传输控制协议，面向连接的协议
        - 两台机器的可靠无差错的数据传输
        - 双向字节流传递
    - UDP(User Datagram Protocol)   -->QQ(多次UDP模仿TCP)
    - 用户数据报协议，面向无连接协议
    - 不保证可靠的数据传输
    - 速度快，也可以在较差的网络下使用
    
### UDP

- 计算机通讯：数据从一个IP的port出发（发送方），运输到另外一个IP的port（接收方）
- UDP：无连接无状态的通讯协议
    - 发送方发送消息，如果接收方刚好在目的地，则可以接受，如果不在
    那这个消息就丢失了
    - 发送方也无法得知是否发送成功
    - UDP的好处就是简单节省，经济

#### 实例

-  DatagramSocket:通讯的数据管道
    - send和receive方法
    - （可选，多网卡）绑定一个IP和Port
- DatagramPacket
    - 集装箱：封装数据
    - 地址标签：目的地IP+Port
Receive:
```java
package JavaLearning_Advanced.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Description: receive
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 0:23
 */
public class UdpRecv {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(3000);
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf,buf.length);
        System.out.println("UdpRecv：等待信息");
        socket.receive(packet);
        System.out.println("UdpRecv:已接受信息");
        String strRecv = new String(packet.getData(), 0, packet.getLength()) + "from" + packet.getAddress().getHostAddress()
                + ":" + packet.getPort();
        System.out.println(strRecv);
        String str = "nice to meet you!!";
        DatagramPacket packet1 = new DatagramPacket(str.getBytes(), str.length(), packet.getAddress(), packet.getPort());
        System.out.println("UdpRecv:即将发送信息！");
        socket.send(packet1);
        System.out.println("UdpRecv:已发送");

    }
}

```

send:
```java
package JavaLearning_Advanced.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Description: send
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 0:24
 */
public class UdpSend {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        String str = "hi ,im a mini robot";
        DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), InetAddress.getByName("127.0.0.1"), 3000);
        System.out.println("UdpSend:我要发送信息了");
        socket.send(packet);
        System.out.println("UdpSend: 发送完毕");
        Thread.sleep(1000);
        byte[] buf = new byte[1024];
        DatagramPacket packet1 = new DatagramPacket(buf, 1024);
        System.out.println("UdpSend：我在等待信息");
        socket.receive(packet1);
        System.out.println("UdpSend：已收到信息");
        String str2 = new String(packet1.getData(), 0, packet1.getLength())+"from"+packet1.getAddress().getHostAddress()
                +":"+packet1.getPort();
        System.out.println(str2);
    }
}

```

### TCP

- TCP协议：有链接、保证可靠的无误差通讯
    1. 服务器：创建一个ServerSocket，等待连接
    2. 客户机：创建一个Socket，连接到服务器
    3. 服务器：ServerSocket接收到连接，创建一个Socket和客户的Socket建立专线连接，后续服务器和客户机的对话
    （这一对Socket）会在一个单独的线程（服务器端）上运行
    4. 服务器的ServerSocket继续等待连接，返回 i.
- 软件服务器有两要求：
    1. 它能够实现一定的功能
    2. 它必须在一个公开地址上对外提供服务
    
- ServerSocket：服务码头
    - 需要绑定Port
    - 如果有多块网卡，需要绑定一个IP地址
- Socket：运输通道
    - 客户端需要绑定服务器的地址和Port
    - 客户端往Socket输入流写入数据，送到服务端
    - 客户端从Socket输出流取服务端过来的数据
    - 服务器反之亦然

- 过程：
    - 服务端等待响应时，处于阻塞状态
    - 服务端可以同时响应多个客户端
    - 服务端每接受一个客户端，就启动一个独立的线程与其对应
    - 客户端或者服务端都可以选择关闭这条Socket通道
    - 实例
        - 服务端先启动，且一直保留
        - 客户端后启动，可以先退出
        
Server:
```java
package JavaLearning_Advanced.TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 13:16
 */
public class TCPServer2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8001);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("来了一个client");
                new Thread(new Worker(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Worker implements Runnable{
        private Socket socket;
        public Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("服务人员已启动");
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String strWord = bufferedReader.readLine();
                    System.out.println("client said:" + strWord + ":" + strWord.length());
                    if (strWord.equalsIgnoreCase("quit")) {
                        break;
                    }
                    String strEcho = strWord + "666";
                    System.out.println("server said:" + strWord + "--------->" + strEcho);
                    dos.writeBytes(strWord + "--------->" +strEcho+ System.getProperty("line.separator"));
                }
                bufferedReader.close();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

```

Client:
```java
package JavaLearning_Advanced.TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 11:47
 */
public class TcpClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8001);
            // 同一个通道，服务端输出流就是客户端的输入流，服务端的输入流，就是客户端的输出流
            InputStream inputStream = socket.getInputStream();//开启通道的输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();//开启通道的输出流
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            BufferedReader brKey = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String strWord = brKey.readLine();
                if (strWord.equalsIgnoreCase("quit")) {
                    break;
                } else {
                    System.out.println("i want to sent "+strWord);
//                    outputStream.write(strWord.getBytes(), 0, strWord.length());//可以这么写
                    dataOutputStream.writeBytes(strWord+System.getProperty("line.separator"));
                    System.out.println("server said"+bufferedReader.readLine());

                }
            }
            dataOutputStream.close();
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
### HTTP

- 在浏览器输入URL地址
- 浏览器将连接到远程服务器（IP=Port(80)）
- 请求下载一个HTML文件下来，放到本地临时文件夹中
- 在浏览器显示出来
 
#### 实例

- URLConnection
    - 获取资源的连接器
    - 根据URL的openConnection()方法获得URLConnection
    - connect方法，建立和资源的联系通道
    - getInputStream方法，获取资源的内容
    
get:
```java
package JavaLearning_Advanced.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 14:14
 */
public class URLConnectionGet {
    public static void main(String[] args) {
        try {
            String urlName = "http://www.baidu.com";
            URL url = new URL(urlName);
            URLConnection connection = url.openConnection();
            connection.connect();
            //打印Http的头部信息
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    System.out.println(key + ":" + value);
                }
            }
            //输出将要收到的内容属性信息
            System.out.println("-------------------");
            System.out.println("getContentType" + connection.getContentType());
            System.out.println("getContentLength" + connection.getContentLength());
            System.out.println("getContentEncoding" + connection.getContentEncoding());
            System.out.println("getDate" + connection.getDate());
            System.out.println("getExpiration" + connection.getExpiration());
            System.out.println("getLastModified" + connection.getLastModified());
            System.out.println("-------------------");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            //输出收到的内容
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```
Post:
```java
package JavaLearning_Advanced.HTTP;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 14:42
 */
public class URLConnectionPost {
    public static void main(String[] args) throws IOException {
        String url = "https://www.usps.com/go/ZipLookupAction.action";
        Object userAgent = "Httpie/0.9.2";
        Object redirects = "1";
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        Map<String, String> params = new HashMap<String, String>();
        params.put("tAddress", "1 Market Street");
        params.put("tCity", "San Francisco");
        params.put("sState", "CA");
        String result = doPost(new URL(url), params,
                userAgent == null ? null : userAgent.toString(),
                redirects == null ? -1 : Integer.parseInt(redirects.toString()));
        System.out.println(result);
    }

    private static String doPost(URL url, Map<String, String> nameValueParis, String userAgent, int redirects) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (userAgent != null) {
            connection.setRequestProperty("User-Agent", userAgent);
        }
        if (redirects >= 0) {
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
        }
        //输出请求的参数
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            boolean first = true;
            for (Map.Entry<String, String> pair : nameValueParis.entrySet()) {
                //参数必须这样拼接a=1&b=2&c=3
                if (first) {
                    first = false;
                } else {
                    out.println('&');
                }
                String name = pair.getKey();
                String value = pair.getValue();
                out.print(name);
                out.print('=');
                out.print(URLEncoder.encode(value, "UTF-8"));
            }
            String encoding = connection.getContentEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
            if (redirects > 0) {
                int responseCode = connection.getResponseCode();
                System.out.println("responseCode:" + responseCode);
                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                        responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                    String location = connection.getHeaderField("Location");
                    if (location != null) {
                        URL base = connection.getURL();
                        connection.disconnect();
                        return doPost(new URL(base, location), nameValueParis, userAgent, redirects - 1);
                    }
                }
            } else if (redirects == 0) {
                throw new IOException("too many redirects");
            }
            //获取html内容
            StringBuilder response = new StringBuilder();
            try (Scanner in = new Scanner(connection.getInputStream(), encoding)) {
                while (in.hasNextLine()) {
                    response.append(in.nextLine());
                    response.append("\n");
                }
            } catch (IOException e) {
                InputStream err = connection.getErrorStream();
                if (err == null) {
                    throw e;
                }
                try (Scanner in = new Scanner(err)) {
                    response.append(in.nextLine());
                    response.append("\n");
                }
            }
            return response.toString();
        }


    }
}

```
### JDK HttpClient (JDK9新增，JDK10更新，JDK11正式发布)

- java.net.http包
- 取代URLConnection
- 支持HTTP/1.1和HTTP/2
- 实现大部分HTTP方法
- 主要类
    - HttpClient
    - HttpRequest
    - HttpResponse

Get:
```java
package JavaLearning_Advanced.HTTP.HttpClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 15:38
 */
public class JDKHttpClientGet {
    public static void main(String[] args) {
        doGet();
    }

    private static void doGet() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
Post:
```java
package JavaLearning_Advanced.HTTP.HttpClient;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 15:43
 */
public class JDKHttpClientPost {
    public static void main(String[] args) {
        doPost();
    }

    private static void doPost() {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://zh-tools.usps.com/zip-code-lookup.htm?byaddress"))
                    .header("User-Agent", "HTTPie/0.9.2")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "tAdress=" + URLEncoder.encode("1 Market Street", "UTF-8") +
                                    "tCity="+ URLEncoder.encode("San Francisco", "UTF-8") +
                                    "sState="+ "CA")
                    ).build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.headers());
            System.out.println(response.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
    
### HttpComponent

Get:
```java
package JavaLearning_Advanced.HTTP.HttpComponet;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 16:53
 */
public class HttpComponentsGet {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)//设置超时时间
                .setConnectionRequestTimeout(5000)//设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        httpGet.setConfig(requestConfig);
        String strResult = "";
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");//获得返回结果
                System.out.println(strResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
Post;
```java
package JavaLearning_Advanced.HTTP.HttpComponet;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020/3/29 0029 17:03
 */
public class HttpComponentsPost {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //获取可关闭的httpClient
        //CloseableHttpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setRedirectsEnabled(false).build();
        HttpPost httpPost = new HttpPost("https://zh-tools.usps.com/zip-code-lookup.htm?byaddress");
        //配置post参数
        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("tAdress", URLEncoder.encode("1 Market Street", "UTF-8")));//请求参数
        list.add(new BasicNameValuePair("tCity", URLEncoder.encode("San Francisco", "UTF-8")));//请求参数
        list.add(new BasicNameValuePair("sState", "CA"));//请求参数
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            // 设置post请求参数
            httpPost.setEntity(entity);
            httpPost.setHeader("User-Agent", "HTTPie/0.9.2");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String result = "";
            if (httpResponse != null) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println(statusCode);
                if (statusCode == 200) {
                    result = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    result = "ERROR Response" + httpResponse.getStatusLine().toString();
                }
            } else {
            }
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

```

## Java调用其他语言

### Python 
- 解释性脚本语言
- 目前版本3
- Jython
    - 将Python源码编译成JVM字节码,由JVM执行对应的字节码,可以很好的与JVM继承
    - 不是Java与Python的连接器
      