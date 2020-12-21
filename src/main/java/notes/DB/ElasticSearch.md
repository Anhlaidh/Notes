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
    - IDF inverse doc frequency
    - 关键词在当前doc出现的次数
    - 每个doc的长度 越长相关度越低 
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
  - 两台机子p0,p1,p2  r0,r1,r2  p挂掉之后r顶上
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
      - 但是实际情况取决于集群拥有的分片和索引以及他们的大小,不一定总是能均匀的分布
   2. ES不允许Primary与Replica放在同一个节点中,并且同一个节点不接受完全相同的两个Replica
   3. 同一个节点允许多个索引的分片同时存在
2. 容错
   1. 向下兼容
   2. 自行恢复的能力
3. ESNode
   - 默认配置  node.master=true  node,data=true 
   2. Master:主节点,每个集群只有一个 专注于集群的管理 协调节点->转发
      1. 尽量避免node.data = true
   3. voting:投票节点
      1. master宕机了投票选举master  数量等于数据节点
      2. 设置了Node.voting_only=true 即使node.master=true 也不会参选
      3. voting_only仍然可以做数据节点(node.data=true)
   4. coordinating:协调节点
    node.master|node.data|node.voting_only|节点详情
      -|-|-|-
    true|尽量false|false|master节点 /候选节点,参选为master
    false|true|true|投票节点,不仅投票,还存数据
    false|false|true|不存数据的投票节点   -> 协调节点
    false|true|false | 数据节点,主要负责数据的查询以及增删改 
4. 投票选举master
   1. 集群节点数量一般是奇数个  投票节点与与数据节点数量相同
      1. 如果是偶数个,默认投票节点减少一个
   2. 步骤
      1. 寻找clusterStateVersion比自己高的节点,想起发送选票
      2. 如果clusterStateVersion一样,则在候选节点(包含当前节点)中选id最小的一个节点,向该节点发送选举投票
      3. 如果一个节点收到足够多的投票并且也向自己投票了,那么该节点成为master开始发布集群状态
5. 脑裂问题
   1. discovery.zen.minimum_master_nodes=N/2+1
   2. 小于等于两台机子的集群会产生脑裂 

## ES 查询语法
- search
  - timeout true/false(默认)
    - 超过指定时间,停止查询,查到多少条返回多少条
- Query String
  - Url
    - `GET /product/_search?from=0&size=2&sort=price:asc`
      - 取前两个 排序
        - 加了排序之后没有相关度分数
          - 因为加了排序之后score就没有意义了  score是null
- QueryDSL  重点
  ```
    GET /product/_search
    {
      "query": {
        "match_all": {}
      }
    }
    # get
    GET /product/_search
    {
      "query": {
        "match": {
          "name": "nfc"
        }
      }
    }

    # sort
    GET /product/_search
    {
      "query": {
        "match": {
          "name": "xiaomi"
        }
      },
      "sort": [
        {
          "FIELD": {
            "order": "desc"
          }
        }
      ]
    }
    # multi match 多字段查询一个关键词
    GET /product/_search
    {
      "query": {
        "multi_match": {
          "query": "nfc",
          "fields": ["name","desc"]
        }
      },
      "sort": [
        {
          "price": "asc"
        }
      ]
    }

    # _source 指定返回字段

    GET /product/_search
    {
      "query": {
        "match": {
          "name": "nfc"
        }
      },
      "_source": ["name","price"]
    }


    #分页 from size
    GET /product/_search
    {
      "query": {
        "match_all": {}
      },
      "sort": [
        {
            "price": "asc"
        }
      ], 
      "from": 0,
      "size": 1
    }
  
  # 全文检索
  GET /product/_search
  {
    "query": {
      "term": {
          "name": "nfc"
  
      }
    }
  }
  
  # 分词器
  GET /_analyze
  {
    "analyzer":"standard",
    "text":"xiaomi nfc phone"
  }
  
  #短语搜索
  GET /product/_search
  {
    "query": {
      "match_phrase": {
        "name": "xiaomi"
      }
      , "bool": {
        
      }
    }
  }
  
  
  #query and filter
  GET /product/_search
  {
    "query": {
      "bool": {
        "must": [
          {"match": {"name": "xiaomi"}},
          {"match": {"desc"  "shouji"}}],
          "filter": [
            {"match_phrase":{"name":"xiaomi"}},
            {"range":{"price":{ "gte":10,"lte":20}}}
          ],
          "should": [
            {"range": {
              "FIELD": {
                "gte": 10,
                "lte": 20
              }
            }}
          ],
          "minimum_should_match": 1
        
      }
    }
  }
  
  
  # 高亮
  GET /product/_search
  {
     "query": {
       "match_phrase": {
         "name": "nfc"
       }
     },
     "highlight": {
       "fields": {
         "name": {}
       }
     }
  }
  
  #scroll search 
  GET /product/_search?scroll=1m
  {
    "query": {
      "match_all": {}
    },
    "sort": [
      {
        "price": {
          "order": "desc"
        }
      }
    ]
  }
  
  GET /_search/scroll
  {
    "scroll_id" : "FGluY2x1ZGVfY29udGV4dF91dWlkDXF1ZXJ5QW5kRmV0Y2gBFjhiSmpqSkhqU1FlR1pfbXJYaTZ2ZVEAAAAAAABoRBZJOWhLcFBtUVJfMlg4SU4wQldrV2VR"
  }
 

  ```
  
 - 关键词
    - `"term:{}"` query-term完全匹配 不会分词
    - `"match":{}` 匹配 模糊匹配 先对输入进行分词,对分词后的结果进行查询
    - `"match_parse":{}`
    - `"match_All":{}` 匹配所有
    - `"multi_match":{}`:根据多个字段查询一个关键词
    - `"_source:{}"元数据,想要查询多个字段`
    - ` "from":0,"size":2`deep-paging 分页
    - `"range":{}` `"gte": NUM` `"lte":NUM` 取范围
    
### 全文检索
- term 
  - 不会被分词
### 短语搜索
- match_phrase
### Query and filter 查询和过滤
- bool
  - must 
  - filter 过滤器,不计算相关度分数,cache
    - 缓存功能
        1. filter并不是每次执行都会进行cache,而是当执行一定次数的时候才会进行cache一个二进制数组,1表示匹配,0表示不匹配,这个次数不是固定的
        2. filter会优先过滤掉稀疏的数据,保留匹配的cache数组
        3. filter cache保存的是匹配的结果,不需要再从倒排索引中去查找比对,大大提高查询速度
        4. filter一般会在query之前执行,过滤掉一部分数据,从而提高query速度
        5. filter不计算相关度分数,在执行效率上较query高
        6. 党员数据发生改变时,cache也会更新
        
  - should 可能满足  or
    - 多个条件,满足minimum_should_match 默认是0
  - must_not 不计算相关度分数 
  
### Deep paging
1. 深度分页
2. 返回结果不要超过1000个
3. 解决办法
    - 避免深查询
    - scroll search
#### scroll search
- 先查询一部分,给查询的加一个标识,下次继续查询后面的
GET /product/_search?scroll=1m  1m是有效期,1s 1ms
    - `"scroll":"1m"` 延期 
## Mapping
-  mapping 就是es数据字段field的type元数据,es在创建索引的时候,dynamic mapping会自动为不同的数据指定相应的mapping
mapping中包含了字段的类型,搜索方式(exact value或者full text),分词器等
- 查看mapping `GET /product/_mappings`
- 搜索方式
    1. exact value 精确匹配:在倒排索引过程中,分词器会将field作为一个整体创建到索引中
    2. full text 全文检索,分词,近义词,混淆词,大小写,词性,过滤,时态转换等(normalization)
- Mapping Parameter
    1. index 是否对当前字段创建索引,默认为true,如果不创建索引,该字段不会通过索引被搜索到,但仍会在source元数据中展示
    2. analyzer 指定分析器(character filter,tokenizer,Token filters)
    3. boost 对当前字段相关度的评分权重,默认是1
    4. coerce 是否允许强制类型转换 
    5. copy_to





