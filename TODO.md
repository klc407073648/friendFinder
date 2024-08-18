# TODO

- [x] 找队伍界面底部显示间距不对，通过加入id="content"组件后，显示正常
- [x] 底部 队伍、个人tabbar跳转显示有问题，定位后发现是 to="team" 应该修正为 to="/team"
- [x] 加入 心动模式 匹配人数的优化
- [x] 普通模式的分页查询待优化
- [x] 前端console.log()打印无法显示[object Object]内容的问题，一个用逗号连接，而不是加号
- [x] 个人信息里应该加入 退出 选项
- [x] 解决队伍tab里搜索队伍时，应该在当前选择状态下搜索
- [x] 发现tab里标签分类补充，tagEnum
- [x] 界面底部tabbar实现点击高亮功能
- [x] 加入队伍后，界面不实时刷新
- [x] 设置界面加入切换账号、和系统信息的介绍
- [x] 目录结构优化，pages里应该以tabbar栏分多个文件夹
- [x] 且应该创建api调用统一控制多个api调用的函数
- [x] 但是没有登录时，不应该有发送消息的按键,不需要考虑因为没有登录也无法做推荐
- [——] 界面底部tabbar 图标已经优化成实底，但是部分图标没有实底图片，待改进
- [x] 点击联系我应该可以发送消息，但是没有登录时，不应该有发送消息的按键
- [ ] 发现tab里标签内容需要考虑转小写再比较
- [ ] 后端针对队伍里的加密密码应该做加密存储
- [ ] team/list/my/create 接口里 没有设置已加入人数
- [x] 无论是心动模式还是普通模式，都应该实现分页查询
- [ ] 如果加入私密队伍，可以发送消息，等队长同意后，才能加入，考虑加入消息提示，和队长同意的逻辑，增加消息tab栏

Spring 配置文件，连接多个DB，好像需要额外设置
研究一下缓存数据的策略，是直接缓存一批数据，然后去缓存中取对应下标内容，还是每次缓存yupao:user:recommand:1001:1:5
//不能直接根据用户查询缓存，因为pageSize和pageNum可能不一样，即每次查询相同范围时，才应该缓存
        String redisKey = String.format("yupao:user:recommand:%s:%s:%s",loginUser.getId(),pageNum,pageSize);

主页的刷新有问题,实际上查询到数据，但是页面不刷新
点击应该更换图标   Uploader 文件上传
整理用户头像的链接，后期是否可以考虑用户头像的上传，这个涉及到存储，例如多个用户的头像一致，是否可以判重只存入一份


私有队伍是否只有管理员和创建人可见  TODO

用户消息传递功能

先完成前端的简单发送，再考虑给多个人发送应该如何操作

初步完成消息发送的调试，需要优化，目前仅是通过首页联系我，跳转到对应消息页，然后只能单条发消息。
发送消息，显示头像交互，这个可能比较难实现

1. 用户A发送消息给服务端，然后指明要发送给目标的用户B；
2. 用户登录B时，查询消息列表，查看目前数据库里是否有未消费的信息，如果有则取出，取出消息后，将对应记录置为逻辑删除

利用消息队列，先把消息发到消息队列里，然后批量入库，节省时间

已经实现通知内容和消息内容
通知内容，待处理将sendId转换成对应的用户名称，通知内容逻辑是每次点击消息页面，都会去拉取当前用户的消息，点击X后会逻辑删除对应消息，表示已读。
消息内容，就是发送消息给用户。

库表设计

```sql
-- 消息表
create table msg
(
    id         bigint auto_increment comment 'id' primary key,
    sendId     bigint                             not null comment '发送方Id',
    targetId   bigint                             not null comment '目标Id',
    content    varchar(1024)                      not null comment '消息内容',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    idDelete   tinyint  default 0                 not null comment '是否删除',
)comment '消息表';
```

ref() 代表什么意思

后端：

伙伴匹配算法
["大一","C++","男"]
["Java","大一","男","篮球"]   如何处理标签排序比对

（如果需要带**权重计算**，比如学什么方向最重要，性别相对次要）

可以像京东界面一样列出多个标签组，选择对应内容


console.log('发送请求：' + config) 
在输出多个对象的时候会以字符串的形式串联成一个字符串输出,所以不建议用逗号连接
 //发送请求：[object Object]
console.log('发送请求：', config) //发送请求： {transitional: {…},

1. 开发主页（默认推荐和自己兴趣相当的用户） 
    前端作了普通模式和心动模式
    普通模式直接返回分页查询的结果
    心动模式是根据编辑距离自动计算前几的用户返回，目前代码里写死为返回前2个

组队功能 

1. 生成分享链接（分享二维码）
2. 用户访问链接，可以点击加入

md转doc
https://www.cnblogs.com/fancy2022/p/16365046.html

TODO 还需要补充docker方式部署
 

