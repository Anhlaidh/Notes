# SpringBoot
## 常用注解
- @ConfigrationProperties(prefix = "person") 松散绑定
    - @value("@{person.age})
    - 配合application.yaml
        person :
            name: xxx
            age: xxx
            likes:
                - xxx
                - xxx
- @Validated 数据验证,例如@Email
- @WebFilter  
    - 重写doFilter  chain.doFilter(req,res) 
    - 重写destroy
## config
- Application
    - 环境
        - -dev
        - -test
    - yaml
        - 松散绑定
        - random.value  int ....随机数
        - 多config  
            - ---分割
    - properties
    
