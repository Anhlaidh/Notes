# Redis
@[TOC]
## 安装&启动
### server
-  redis_6379
- redis-server /etc/redis/6379.conf
- `--loadmoudle`
### client
## 入门
- 无数据类型
- 默认16个库(分为16个独立的区域)
- redis-cli -p xxxx -n [指定库]
- 方法与类型绑定
    - type 获取值的类型
- 二进制安全
    - 字节流
    - 字符流
- redis-cli --raw 格式化
- help
- keys *
- flushall
- flushdb
### 数据类型
help @String
- String
    - encoding-str
        - set
            - 生存时间
            - nx - 不存在的时候赋值
            - xx  - 存在的时候才能更新 
        - get
        - mset - 多个存
        - mget - 多个取
        - append - 追加
        - getrange [start] [end]
            - 正反向索引
        - setrange 修改
        - strlen  -> length
        - msetnx 原子性多操作,一个失败就取消操作
    - encoding-int(省去之后计算的判断,本质还是字节流)
        - incr +1
        - incrby [value] n
        - decr [value] 减1
        - incrbyfloat [value] n 加浮点数
    - bitmap
        - bitset 设置字节中的bit `offset是指bit`
        - bitpos 第一个1 `offset是指字节`
        - bitcount  `offset是指字节`
        - bitop
- List 
    - Lpush
    - Rpush
    - Lpop
    - Rpop
    - lrange
    - Lindex
    - Rindex
    - Lrem
    - Rrem
    - llen
    - blpop
        - 阻塞队列,单播队列
        - 先进先出
    - Ltrim
- Hash map(k-v)
    - hset
    - hget
    - hgetall
    - hincrby
    - hincrybyfloat
    - `keys sean*`
- set
    - sadd
    - scard
    - 交集并集差集
        - sinter(store)
        - sunion
        - sdiff
    - sismember
    - smembers
    - srandmember 
    - spop
- sorted_set
    - zadd
    - zrange #
    - zrevrange #
    - zrangebyscore
    - zscore
    - zrank
    - zincrby 实时维护
    - 交集并集差集
        - zunionstore weights 聚合指令
    - zremovebyscore
    - zremovebyrank
        - 滑动窗口
    
## 进阶
### 管道
- `echo -n  "set k1 99\n incr k1 \n get k1" | nc xxxx 6379`
    - 建立tcp通信就能跟redis对话
### 发布/订阅
### 事务
- transaction
    - multi 开启事务
    - exec 执行事务
    - discard 取消事务
    - watch 
        - 乐观锁
        - cas
            - A
                1. `watch k1`
                2. `multi`
                3. `get k1`
                4. `keys *`
            - B
                1. `muti`
                2. `keys *`
                3. `set k1 xxxxx`
                4.  `exec`
                5. A`exec`
                    - 数据被b修改过
                    - 不执行事务
- 缓存穿透
    - module   redisbloom
        - bloom过滤器
        - 布谷鸟过滤器
        - counting bloom
    - 查找没有的东西 
- 缓存和数据库的区别
- config
    - 内存
        - 回收策略
            - noeviction 不丢,做数据库用
            - lfu 碰了多少次
            - lru 多久没碰
            - random
            - ttl
- [有效期](http://www.redis.cn/commands/expire.html)
    - ttl 按存活时间来
        - ttl + key 存活时间
        - key的有效期,访问延长?no
        - 发生写,会剔除过期时间(没设置新的时间?)
        - expire 设置时间(多少秒)
            - expireat 指定时间(倒计时,且不能延长)
    - 被动/主动
    