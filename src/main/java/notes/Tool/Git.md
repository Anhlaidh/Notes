# Git
- [练习](https://learngitbranching.js.org/?locale=zh_CN)
## 指令
### local
- `git add <>`
- `git commit  `
    - `git commit --amend` 修改当前提交
    - `git commit -m "" `提交,-m为信息
    - 提交
- `git checkout xxx`
    - `git checkout -b xxx`
        - 创建并跳转至分支xxx
- `git branch xxx`
    - 创建分支xxx
- `xxx^`  `xxx~<num>`向上跳几步
    - 相对,绝对
    - `^<num>` 选择第几个父提交
- `git reset xxx` 将当前分支指向调整至xxx
- `git rebase`
    - 取出一系列的提交记录，“复制”它们，然后在另外一个地方逐个的放下去。(当前以xx为根节点,如果同分支,则跳转过去)
- `git reset`&&`git revert`
    - reset,跳转到xxx,回退,适用于local
    - revert,创建一个回退备份并移至备份指针,适用于远程库
- `git cherry-pick xxx,xxx` 从head开始依次填补xxx

- `git rebase -i xxx` 动态rebase,弹出ui界面

- `git tag xxx <hash>`/`git tag xxx`
    - 版本
- `git describe <hash>` 查看信息/查看当前信息
   
### 远程库
- `git clone xxx` 从远程库clone
    - 分支不能commit,会head分离
- `git fetch`
    - 从远程库拉取分支,包含中间提交
    - fetch 和 push
    方向相反,参数也是
        - `git fetch origin <remotebranch>:<localbranch>` 如果分支不存在,自定创建
    - fetch空值,原地创建
- `git pull`
    - 相当于 `git fetch`+ `git merge o/master`
- `git push` 推送至远程库,且o/master移至当前master
    - `git push 库名 分支名`指定提交哪一个分支
    - `git push origin <source>:<destination>` 将指定提交push到远程库,如果远程库没有该destination,会创建并提交,非常灵活
    - push 空值,删除指定以及远程库的当前分支
- `git pull --rebase`
    - 相当于  `git fetch`+ `git rebase`
    - 特殊情况 , `git pull <remote> <o/branch>:<unkownbranch>`
        - 拉取一个分支,拉取到一个不存在的分支,会先在本地o/branch上创建分支,然后拉取,然后与当前指向的分支合并,合并为当前指向的分支,而不是远程拉取的分支
- 远程追踪
    - `git checkout -b aaa o/bbb`指定远程追踪
    - `git branch -u o/bbb aaa`第二种方法,不加local分支名则与当前绑定

