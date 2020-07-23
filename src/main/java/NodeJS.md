# Node

## REPL
- read
- exec
- print
- loop

## 模块
- 规范 
    - 一个文件就是一个模块
    - 通过exports和module.export来导出模块中的成员(生命模块中哪些功能可以使用)
    - 通过require来加载模块
### 语法

#### CommonJS
- 导出成员
    - export.属性名 = 值
- 引入模块
    - var 变量名 = require('路径')
```javascript
//引入b模块
var b = require('./b')
console.log(b)
b.add()
```
```javascript
function add() {
    console.log('this is add')
}

function del() {
    console.log('this is del')
}
exports.add = add;
exports.del = del;
```
#### 第三方Node.js模块 npm
#### 内置node.js模块(os/path/url模块)

1. 引入模块
2. 使用方法
```javascript
//引入官方模块(注:官方模块不需要定义)
var os = require('os')
var path = require('path')
var url = require('url')
// const {
//     url
// } = require('inspector')
//需求
//获取系统总内存
var mem = os.totalmem() / 1024 / 1024
console.log(mem)
//文件后缀
console.log(path.extname('C:\Users\china\Downloads\QQ号码_439484483.txt'))
//获取表单get提交参数https://www.baidu.com/s?ie=UTF-8&wd=aaa
var obj = url.parse('https://www.baidu.com/s?ie=UTF-8&wd=aaa', true)
console.log(obj.path)
console.log(obj.href)
```

- fs模块
写入文件
```javascript
//1. 引入官方的fs模块
const fs = require('fs');
// 2. 调用成员writeFile创建文件
fs.writeFile('./a.txt', '你好', function (err) {
    //err没数据 写入失败
    if (err) {
        console.log(err)
        return;
    }
    //err有数据 写入成功
    console.log(" 没毛病,写入成功")
})
```
读取文件
```javascript
//1. 引入fs
const fs = require('fs');
//2. 调用fs模块的成员 readFile 来读取a.txt
fs.readFile('./a.txt','utf-8', function (err, data) {
    if (err) {
        //如果err为真则代表读取失败(如文件不存在)
        console.log(err)
        return;
    }
    console.log(data);

});
```
- http 模块
```javascript
//1. 引入http模块
const http = require('http');
//2. 创建web服务器对象(请求和响应)
let server = http.createServer();
//3. 监听请求 -> 响应内容
server.on('request', function (request, response) {
    console.log('接收到用户请求');
    console.log(request.url);
    //有请求必须有响应
    if (request.url == '/') {
        response.end('hello');
    }else if (request.url == '/index') {
        response.end('this is index');
    } else {
//设置编码方式
        response.setHeader('content-type','text/html;charset=utf-8')
        response.end('未找到')
    }

});
// 4. 启动服务
server.listen(8080, function () {
    console.log('服务器启动成功,域名localhost:8080');

});
```
