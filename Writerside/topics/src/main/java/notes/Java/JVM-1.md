# JVM
## 学术名词
- TLAB (Thread Local Allocation Buffer)
## 垃圾回收器
- 分代
    - CMS +ParNew
    - Serial+Serial Old
    - Parallel Scavenge+Parallel Old
- 分区
    - G1
    - ZGC
    - Shenandoah
## 调优
- 什么是调优
    1. 根据需求进行JVM规划和调优
    2. 优化运行JVM环境(慢,卡顿)
    3. 解决JVM运行过程中出现的各种问题(OOM) out of memery
- Java参数
    - 标准参数
    - 非标准参数 -X
    - 调优 -XX
## 参数
- `-Xms200M` 最小内存200M
- `-Xmx200M` 最大内存200M
    - 设置成一样,防止内存抖动
- `-XX:+PrintGC` 打印出来
- `-XX:+HeapDumpOnOutOfMemoryError`

## 命令
- `java -Xms2M -Xmx2M -XX:+PrintGC problem`
- `jps` 查看正在运行的编号 
- `jinfo 编号`查看进程信息
- `jstack`
- `jmap`
- `jstatus`
- *`arthas`
    - `dashboard` 仪表盘
    - `jvm` 类似jinfo
    - `thread` 类似jstack
        - `thread -b` 找死锁
        - `heapdump` 导出stack
        - `jad` 即时反编译
        - `redifine` 重新替换class