## 一、Storm 集群搭建与可视化图

![1559487642337](https://github.com/sundial-dreams/storm-task/blob/master/src/main/java/storm/1559487642337.png?raw=true)

#### 操作步骤

1. 环境window10，首先下载Zookeeper以及Storm

2. 配置Zookeeper集群

   + 在conf目录下添加zoo.cfg文件

   + 添加一下内容

     > tickTime=2000
     >
     > initLimit=10
     >
     > syncLimit=5
     >
     > dataDir=./data
     >
     > clientPort=8000
     >
     > admin.serverPort=9003
     >
     > server.1=127.0.0.1:3000:3888
     > server.2=127.0.0.1:3001:3889
     > server.3=127.0.0.1:3002:3890

   +  根据dataDir，在bin新建一个data目录，并在打他目录下新建myid文件，文件内容为1

   + 将Zookeeper整个目录复制两份并修改对应的zoo.cfg

     > 修改clientPort=8001或clientPort=8002
     >
     > 修改myid文件为2或3

3. 启动Zookeeper集群，进入bin目录，`./zkServer` 启动zookeeper节点

4. 配置storm.yaml

   > storm.zookeeper.servers:
   >
   > ​    \- "127.0.0.1"
   >
   > storm.zookeeper.port: 8001
   >
   > storm.local.dir: "./data"
   >
   > nimbus.seeds: ["127.0.0.1"]
   >
   > supervisor.slots.ports:
   >
   > ​    \- 6700
   >
   > ​    \- 6701
   >
   > ​    \- 6702
   >
   > ​    \- 6703
   >
   > ​    \- 6704
   >
   > ​    \- 6705
   >
   > ​    \- 6706
   >
   > ​    \- 6707

5. 启动storm
   + `python storm.py nimbus`
   + `python storm.py supervisor`
   + `python storm.py ui`

6. 提交topology

   > ```bash
   > ./storm.cmd jar ./storm.jar storm.StormCluster.Main topology-third
   > ```

7. 在浏览器打开localhost:8080即可查看提交的topology

### 二、Storm可靠性

![1559648531018](https://github.com/sundial-dreams/storm-task/blob/master/src/main/java/storm/1559648531018.png?raw=true)

基本步骤，提交topology

```bash
./storm.cmd jar ./storm.jar storm.StormRedis.Main topology-forth
```

