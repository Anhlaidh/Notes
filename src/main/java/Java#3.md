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
### 静态导入
- import static
- 导入类中的静态方法
    - 使用时可以直接写方法名,不写类名
- 少用*
- 如果重名需要补充类名
    