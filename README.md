## 背景
企业以springboot框架作为一个业务开发的脚手架，随着业务的迭代，以及访问量提升，服务实例递增，会导致海量日志产生；无规则的海量日志会存在以下问题
- 1、日志与日志之间缺少关联关系
- 2、日志信息维度不全，无法定位问题
- 3、日志难以清洗，直接形成报表比较难
- 4、服务出现问题，开发者感知慢造成业务损失
- 5、开发者定义业务异常维度不够，容易误报漏报服务异常
- 6、日志代码散落在各个角落，代码不够精简，项目难以维护

此插件可以解决以上问题

## 什么是bpm
bpm 是 Business Performance Management 缩写，log-agent-framework是bpm 的实现，它是一款日志中间是一款基于java agent 的日志插件，用于结构化输出日志数据，便于后期的问题排查，以及业务的报警；通过maven接入，将spring 项目的日志以json输出。
## 产品定位
| 类型 | 用途 | 代表 |
| --- | --- | --- |
| NPM | 用于监控设施的基础信息，如网络、io、cpu 等，一般运维使用 | prometheus |
| APM | 用于监控应用的数据链路，日志数据大，且详细，一般运维使用 | pinpoint、SkyWalking |
| **BPM** | **用于监控重要业务，日志量少，且精确，一般开发人员使用** |
|

## 优点

- 对于项目维护：采用无侵入方式，较少冗余的代码
- 对于程序开发：提供公共位置的日志收集，加快开发进度
- 对于问题排查：采用标准的日志格式，可根据类型快速过滤排查
- 对于项目预警：采用模块化+api，可自定操作
## 什么时候用

-  后端开发人员排查bug，定位问题
-  前端人员开发优化资源加载性能
-  leader 需要重要报表数据，提供决策
-  异常报警
## 如何构建日志系统

-  日志生产：规定日志规范json，以及实现
-  日志搬运：sdk、filebeat、日志汇总、阿里日志服务、oss
-  日志清洗：logstash、json、脚印、柯南系统、es
-  日志存贮：hive、es、mysql
-  日志消费：kibana、grafa、报警、报表
## 日志级别选型
| 建议 | 日志级别 | 保留时间 |
| --- | --- | --- |
| 不建立，或者自建elk | 小型或者微型 | 日志保留时间建议 |
| 自建elk，或者阿里 elk 或者阿里日志服务 | 中型 | 小于1天 |
| 阿里日志服务 | 大型 | 小于3天 |

### Bpm日志数据结构
![logJson.png](img%2FlogJson.png)

### 日志层次划分

针对整个系统而言，开发者主要系统三个层次的日志；系统入口层的日志、系统出口层的日志、用户自定义的日志；下面表示各个层次日志的代表

| 层次 | 例子 | 日志type |
| --- | --- | --- |
| 入口日志 | controller、httpServlet | ACCESS |
| 出口日志 | http、hession | THIRD |
| 用户自定义日志 | service、自定义log | SERVICE |

### 日志aop 切面
| 切面 | 例子 |
| --- | --- |
| 类上的注解切面 | @RestController  |
| 方法上的注解切面 | @RequestMapping @PostMapping |
| 正则切面 | "^com.bb.bbfff.code.util.HttpUtils$:THIRD": !!seq|
精确切面 配置
@HOOk
instruments：array【injectionClass】
skipNestedCalls：boolean【跳过嵌套切点，默认true】

eg:@Hook(instruments = {"javax.servlet.http.HttpServlet"})


@Before
method：array【injectionMethod】
备注:默认还通过参数的类型，和个数匹配

eg:@Before(method = {"addRequestHeaders"})
public void before(HessianConnection conn) {
LogUtils.insertEmptyAccessId();
conn.addHeader(LogConstants.HEADER_REQUEST_ID, LogUtils.getAccessId());
}


@After：
method：array【injectionMethod】
@Return ：Object【返回值】
@Thrown：Thrown【方法异常】
备注:默认还通过参数的类型，和个数匹配

eg:@After(method = {"invoke"})
public void after(Object proxy, Method method, Object[] args, @Returned Object ret, @Thrown Throwable t) {
String executeTime = LogUtils.getContext(method.toString());
executeTime = String.valueOf(System.currentTimeMillis() - Long.parseLong(executeTime));
LogUtils.hessianLog(executeTime, t, ret, method, args);
}

Hook 参数配置

| key                                  | 说明                                                         | 默认值                             |
| ------------------------------------ | ------------------------------------------------------------ | ---------------------------------- |
| `promagent.fastHooks.scheduledPack`  | 默认收集定时任务日志`收集该包下的@scheduled`                 | null                               |
| `promagent.agent.appName`            | 项目名                                                       | appName                            |
| `promagent.agent.callClass`          | 回调class                                                    | `com.cyou.agent.core.Logger`       |
| `promagent.agent.debug`              | classLoader 调试                                             | false                              |
| `promagent.agent.headers`            | 收集headers，all：收集全部header，none：不收集，headerA:headerB:headerC：收集header列表 | `Arrays.asList<"none">`            |
| `promagent.agent.ignoreSignatures`   | 不收集方法集合日志                                           | `new ArrayList<String>`            |
| `promagent.agent.mdcLogId`           | 传递到Mdc 中的 LogId                                         | access_id                          |
| `promagent.agent.retMaxLength`       | 收集ret 最大长度                                             | 默认20480                          |
| `promagent.agent.skipRetSignatures`  | 不收集放回的值的方法签名集合                                 | `new ArrayList<String>`            |
| `promagent.agent.traceId`            | 传递到其他项目的LogId                                        | X-REQUEST-ID                       |
| `promagent.fastHooks.controllerPack` | 默认收集controller日志，收集该包下的@RequestMapping、@PostMapping、@GetMapping、@DeleteMapping、[@PutMapping ](/PutMapping ) |                                    |
| 日志                                 | null                                                         |                                    |
| `promagent.fastHooks.scheduledPack`  | 默认收集定时任务日志，收集该包下的[@scheduled ](/scheduled ) | null                               |
| `promagent.hooks.annClassHook`       | 类注解回调[@Controller ](/Controller )                       | `new HashMap<String,List<String>>` |
| `promagent.hooks.annMethodHook`      | 方法注解回调Hook 例如：[@RequestMapping ](/RequestMapping )  | `new HashMap<String,List<String>>` |
| `promagent.hooks.annRegHook`         | 正则回调，详细例子见 com.cyou:log-agent-load中的 hook.yml 配置文件 | `new HashMap<String,List<String>>` |
| `promagent.load.agentJar`            | 加载agentJar 路径                                            | null                               |
| `promagent.load.result`              | 加载结果，true：加载成功，false：加载失败                    | false                              |
| `promagent.load.time`                | 加载agent 花费时间                                           | -1                                 |



callbackMethodName和callbackFrameErrorMethodName传递参数类型

| 参数名      | 参数类型  | 含义     |
| ----------- | --------- | -------- |
| executeTime | String    | 执行时间 |
| throwable   | Throwable | 方法异常 |
| ret         | Object    | 返回的值 |
| method      | Method    | 执行方法 |
| arguments   | Object[]  | 方法参数 |
```

### 正则收集例子

```yaml
# hook 文件模板
#annMethodHook:                                                               # 方法注解日志打印
#      "^com.bb.*": !!seq                                                     # 正则表达式:^com.bb.*的package
#           - "org.springframework.web.bind.annotation.RequestMapping:ACCESS" # 日志打印 @RequestMapping 修饰的方法
#           - "org.springframework.web.bind.annotation.PostMapping:ACCESS"    # 日志打印 @PostMapping 修饰的方法
#           - "org.springframework.web.bind.annotation.GetMapping:ACCESS"     # 日志打印 @GetMapping 修饰的方法
#           - "org.springframework.web.bind.annotation.DeleteMapping:ACCESS"  # 日志打印 @DeleteMapping 修饰的方法
#           - "org.springframework.web.bind.annotation.PutMapping:ACCESS"     # 日志打印 @PutMapping 修饰的方法
#           - "org.springframework.scheduling.annotation.Scheduled:CRON"      # 日志打印 @Scheduled 修饰的方法
#
#annClassHook:                                                                # 类注解日志打印
#      "^com.bb.*": !!seq                                                     # 正则表达式:^com.bb.*的package
#           - "org.springframework.web.bind.annotation.RestController:ACCESS" #日志打印 @RestController 修饰的方法
#regHook:                                                                     # 正则日志打印
#      "^com.bb.bbfff.code.util.HttpUtils$:THIRD": !!seq                      # 正则表达式:^com.bb.bbfff.code.util.HttpUtils$ 的package，日志类型为 THIRD，默认为SYSTEM
#           - ".*post.*"                                                      # 正则表达式：.*post.* 的所有方法
#      "^com.bb.bbfff.code.plpay.common.BaasHttpUtil$:THIRD": !!seq
#           - "[a-zA-Z].*"                                                    # 正则表达式：[a-zA-Z].* 的所有方法，不能直接使用.*,需要加限定词[a-zA-Z].*
#      "^com.bb.common.util.HttpClientProxy$:THIRD": !!seq
#           - "[a-zA-Z].*:1"                                                  # 正则表达式：[a-zA-Z].*:1 的所有方法，方法参数个数为1
#      "^com.bb.openplatform.http.HttpUtil$:THIRD": !!seq
#           - ".*Response.*"                                                  # 正则表达式：.*Response.* 的所有方法
```
### ClasssLoader 加载

-  对于agent，他的平级目录class文件是被appClassLoader引用，promagent-agent下面的四个class，可被全局的class所访问
-  ClassLoaderCache作为单例，不同的classLoader，例如webappClassLoader回调的时候，ClassLoaderCache会将共享的jar分享出去
-  因为ClassLoaderCache先查询 sharedClassLoader，ClassLoaderCache单例，所以sharedClassLoader对象唯一，所以全局会共享一个Delegator【Delegator用于精确匹配回调方法】

项目接入：

- 添加一个maven插件 和依赖
```
<dependency>
    <groupId>io.promagent</groupId>
    <artifactId>log-agent-load</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>copy</id>
            <phase>process-resources</phase>
            <goals>
                <goal>copy</goal>
            </goals>
            <configuration>
                <artifactItems>
                    <artifactItem>
                        <groupId>io.promagent</groupId>
                        <artifactId>log-agent-builder</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <overWrite>true</overWrite>
                        <destFileName>log-agent.jar</destFileName>
                        <outputDirectory>${project.build.directory}/classes/</outputDirectory>
                    </artifactItem>
                </artifactItems>
                <overWriteReleases>false</overWriteReleases>
                <overWriteSnapshots>false</overWriteSnapshots>
                <overWriteIfNewer>true</overWriteIfNewer>
                <stripVersion>true</stripVersion>
             </configuration>
        </execution>
    </executions>
</plugin>
```

- 添加hook.yml 配置文件
```
promagent:
  agent:
    appName: testAgent
    headers: !!seq
      - "all"
  fastHooks:
     controllerPackage: io.promagent.agent.test.controller
  load:
    agentJar: "E:\\study\\promagent\\promagent-log\\log-agent-builder\\target\\log-agent.jar"
```

_备注：更多配置文件先hook 说明_
启动方式

- spring 启动：添加 @ComponentScan(basePackages={"io.promagent.agent"})
- 非spring 启动：new AgentBootstrap().init()
### 建议
    为了是用户无感知升级，建议通过jvm参数，统一配置管理，以及升级
### 后期规划

-  spring boot 自动加载
-  配置文件动态更新
### 问题反馈

- 微信：javazhangyi
- 邮件：javazhangyi[@163.com ](/163.com )



![wechatPay.jpg](img%2FwechatPay.jpg)

<center><h1>来杯咖啡</h1></center>
