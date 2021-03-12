# Redis
## 概述
- 磁盘速度比内存慢了10w倍
- IOBuffer 成本问题
- 磁盘与扇区:
    - 一个扇区512Byte
    - 操作系统,无论你读多少,最少4k
- 缓存
    - memcached
    - redis  