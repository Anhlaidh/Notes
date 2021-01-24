# Tank War 设计
## 笔记
- 同时按下两个方向键
    1. KeyEvent传进来,switch一次只处理完一个就break了,以至于不能斜向移动
        - 方向键设置为boolean press事件是变成true release时间变成false
        - 方向固定的话,使用枚举避免错误可能  enum
    2. 该在哪里重写呢?
## 面向对象编程的原则

- 封装
    - 每个x,y,dir都应只属于一个坦克,封装出来,避免代码冗余
        - 如果封装完直接tank.x,就相当于封装再拆封,破坏了封装性
            - 画tank把画笔给tank,让tank自己画自己
- 继承
- 多态
- 持有对方的引用
### 思路
- 编程的竞争力在技术还是在设计
- 为什么用tank war
    - 学面向对象举例汽车,动物,有功能,有联系
## 主流程

1. 学习Frame几个属性方法
2. 设置方向移动
    1. 移动控制需要改进
        1. 设立enum,setDir
        2. repaint
3. 封装tank,移交画笔控制权(设计的概念)
4. 处理tank静止状态
    - tank.moving = false
        - 如果ismoving,移动,否则return
        - 在setDir的时候判断是否bU bR bD bL是否全为false 否则moving
5. 创建Bullet 子弹
    - 分析需要的属性, x,y,speed,dir
        - move方法相同,需要重构吗?
6. 双缓冲,不用深究
    - 闪烁是因为加载速度和屏幕刷新速度不一致
    - 先在内存中画好,再刷到屏幕上
    - 创建一个图片,画一遍,然后g.drawImage把图片画到屏幕上
    - repaint先update,再paint
7. 子弹发射
    - 按键绑定,tank.fire()创建子弹,怎么把子弹放到主窗口渲染出来呢?
    - 多个子弹 list,遍历print
        - 不会remove,内存泄漏!!!!不清除会溢出
            - 子弹飞出窗口就remove掉
                - 子弹添加live属性
                - 出去就remove this ,所以需要拿到frame的引用,至此,tank和bullet都拿到了frame的引用了
8. Junit test
    - 测试路径
    - classLoader来找
9. load图片
