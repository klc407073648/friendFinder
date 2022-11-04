# 部署项目

## 替换宏定义

利用build.sh脚本替换实际部署的环境信息，然后将对应文件拷贝到frontend和backend路径下

## 前端

1. 利用WebStorm生成dist文件

![](./images/前端生成dist.png)

2. 将对应的docker配置文件和Dockerfile放到对应路径下

```
[root@VM-16-6-centos frontend]# tree -L 1
.
|-- dist
|-- docker
`-- Dockerfile
```

3. 执行命令

```
docker build -t friend-finder-frontend:v0.0.1 .
docker run -p 3000:3000 -d friend-finder-frontend:v0.0.1
```

## 后端

1. 利用Idea ,执行mvn package生成jar

![](./images/后端生成jar.png)

2. 将对应的/target/friend-finder-0.0.1-SNAPSHOT.jar和Dockerfile放到对应路径下

```
[root@VM-16-6-centos backend]# tree
.
|-- Dockerfile
`-- target
    `-- friend-finder-0.0.1-SNAPSHOT.jar
```

3. 执行命令(TODO,目前容器方式执行有问题)

```
docker build -t friend-finder-backend:v0.0.1 .
docker run -p 8080:8080 -d friend-finder-backend:v0.0.1
```

临时解决：

```
# 直接在 /target下执行
java -jar ./friend-finder-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
