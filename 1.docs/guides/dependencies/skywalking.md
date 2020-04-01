# Skywalking

## 如何集成

> Skywalking属于外部依赖，在接入之前请先准备相应基础设施

### Download agent

要与skywalking集成，你需要使用java agent。

你可以通过执行以下命令下载skywalking的java agent

#### linux/macos

```shell
curl -# -o agent.tar.gz http://mirrors.tuna.tsinghua.edu.cn/apache/skywalking/6.4.0/apache-skywalking-apm-6.4.0.tar.gz 
mkdir -p agent
tar -zxvf agent.tar.gz
rm -rf agent/skywalking
mv apache-skywalking-apm-bin/agent agent/skywalking
rm agent.tar.gz
rm -rf apache-skywalking-apm-bin
```

#### windows

暂不支持windows系统，请前往http://skywalking.apache.org/downloads/手动下载相关相应版本，并将压缩包agent目录下的内容放至项目agent/skywalking下

### Run with IDE

请在IDE的启动VM参数中加入以下配置

```text
-javaagent:agent/skywalking/skywalking-agent.jar
-Dskywalking.agent.service_name=dp-demo
-Dskywalking.collector.backend_service={sw_service_address}
```

### Run with *.jar

请参考[Start via process](../quickly_start.md#start-via-process)，并加入以下启动参数

```text
-javaagent:{project_home}/agent/skywalking/skywalking-agent.jar
-Dskywalking.agent.service_name=dp-demo
-Dskywalking.collector.backend_service={sw_service_address}
```

### Run with Docker

请参考[Start via container](../quickly_start.md#start-via-container)，并通过以下环境变量控制skywalking相关配置

|**环境变量**|**描述**|**默认值**|
|--|--|--|
|SW_SERVICE_NAME|注册到skywalking时使用的服务名|dp-demo|
|SW_SERVICE_ADDR|skywalking服务地址|无|

> 注意：以上环境变量应修改`run.sh`脚本，通过`-e`参数传入

## Trace ID

如果启动时接入了skywalking，应用在输出日志到控制台或文件时会为每一条记录带上 `trace id`，方便在日志系统中对同一请求由不同服务产生的日志信息进行聚合。

你可以通过`resources`目录下的`logback-spring.xml` `console-appender.xml` `file-appender.xml`配置日志的输出格式。
