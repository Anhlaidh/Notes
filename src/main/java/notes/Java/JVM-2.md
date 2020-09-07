# JVM
## 概念
- jvm跟java无关
    - 任何语言->class->JVM
- jvm是一种规范
- 虚构出来的计算机
    - 字节码指令集(汇编语言)
    - 内存管理:栈 堆 方法区等
- 常见的JVM实现
    - Hotspot
        - Oracle官方,做实验用的jvm
    - Jrockit
        - BEA,最快jvm
        - 被Oracle收购,合并于hotspot
    - TaoBaoVM
        - hotspot深度定制版
    - LiquidVM
        - 直接针对硬件
    - azul zing
        - 垃圾回收的业界标杆
    - J9 -IBM
    - Microsoft VM
- JDK JRE JVM
    - JDK = jre + development kit
    - JRE = jvm + core lib
    - JVM
## Class File Format
- 二进制字节流
- 数据类型 u1 u2 u4 u8 和_info