# Elasticsearch
- ES不等于搜索引擎
- 分布式的搜索,存储,数据分析引擎
- 优点:
  1. 面向开发者友好,屏蔽了Luncene的复杂特性,集群自动发现
  2. 自动维护数据在多个节点上的建立
  3. 帮我们做搜索请求的负载均衡
  4. 自动维护冗余副本,保证了部分节点宕机的情况下仍然不会有任何数据丢失
  5. ES基于Lucene提供了很多高级功能:符合查询,聚合分析,基于地理位置
  6. 对于大公司,可以构建几百台服务器的大型分布式集群,处理PB级别数据 
  7. 相遇传统数据库,提供了全文见检索,同义词处理,相关度排名,聚合分析以及海量数据的近实时(NTR)处理,这些传统数据库完全做不到
- 应用领域
  1. 搜索引擎
  2. 各大网站的用户行为日志(点击,浏览,收藏,评论) 
  3. BI 数据分析
  4. Github
  5. ELK (Elasticseach(数据存储) Logstash(日志采集) Kibana(可视化))
## 核心概念和原理
- 搜索
  - SQL 用了%不能用索引 ->性能差
    - like % 搜索时间复杂度O(n)
  - 不能分词
- 倒排索引
  1. 分词
  2. 生成相关度 *
  3. 排序
  4. 空间换时间
  - 数据结构
    - document List
    - 关键词在每个doc中出现的次数 TF term frequency 词频
    - IDF
    - 关键词在当前doc出现的次数
    - 每个doc的长度 
    - 包含这个关键词的所有doc的平均长度
- 核心概念
  - 集群:每个集群至少包含两个节点
  - Node:集群中的节点 一个节点不代表是一台机器
  - Filed: 一个数据字段,与index和type可以定位一个doc
  - Document:ES最小的数据单元 Json
  - Type:逻辑上的数据分类
  - Index:一类相同或者相似的doc
  - 分片shard
    1. 一个index包含多个shard,默认5p,默认每个p分配一个r,p的数量在创建索引的时候设置,如果想修改,需要重建索引
    2. 每个shard都是一个lucene实例,有完整的创建索引的处理请求能力
    3. ES会自动在nodes尚未我们zuoshard均衡
    4. 一个doc不可能同时存在于多个PShard,但是可以同时多个Rshard 
    5. p和对应的r不能同时存在于同一个节点,所以最低的可用配置是两台节点,互为主备
    - 横向扩容 
    - Parimary Shared
    - Replica Shard
- 容错
  - 两台机子p0,p1,p2  r0,r1,r2  p挂掉之后r顶上,但是不能进行写(?),所以会有数据丢失
  - 容错机制
    1. master选举
       1. findMaster
          1. 脑裂  可能会产生多个master节点
       2. 判断自己是否为master
       3. 广播/findMaster
    2. replica容错
    3. 重启故障机
    4. 数据恢复
       - 只拷贝新增数据 不是全量拷贝  
## 使用
- 健康值检查
### CRUD
- Query String search  类似于Url挂参数搜索
- Query DSL
- Query and filter
```
GET /_cat/health?v
PUT /test_index?pretty
GET _cat/indices
DELETE /test_index?pretty

PUT /product/_doc/1
{
    "name":"xiaomi erji",
    "desc":"erji zhong de huangmenji",
    "price":1999,
    "tags":["low","bufangshui","yinzhicha"]
}
PUT /product/_doc/2
{
    "name":"xiaomi shouji",
    "desc":"erji zhong de huangmenji",
    "price":2999,
    "tags":["low","bufangshui","yinzhicha"]
}
PUT /product/_doc/3
{
    "name":"xiaomi nfc shouji",
    "desc":"erji zhong de huangmenji",
    "price":3999,
    "tags":["low","bufangshui","yinzhicha"]
}
PUT /product/_doc/4
{
    "name":"xiaomi yinxiang",
    "desc":"erji zhong de huangmenji",
    "price":4999,
    "tags":["low","bufangshui","yinzhicha"]
}


GET /product/_doc/_search
{
  "query":{
    "match":{
      "name":"shouji xiaomi"
    }
  },
    "sort":[
      {"price":"desc"}]
    
}
POST /product/_doc/1/update
{
  "doc":[]
}

DELETE /product/_doc/1
```
## ES分布式文档系统
1. 实现高可用
   1. ES在分配单个索引的分片时会将每个分片尽可能分配到更多的节点上
      1. 但是实际情况取决于集群拥有的分片和索引以及他们的大小,不一定总是能均匀的分布
   2. ES不允许Primary与Replica放在同一个节点中,并且同一个节点不接受完全相同的两个Replica
   3. 同一个节点允许多个索引的分片同时存在