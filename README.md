项目介绍：log-agent-framework 是一款日志中间，通过maven接入，将spring 项目的日志以json输出

优点：

- 对于项目维护：采用无侵入方式，较少冗余的代码
- 对于程序开发：提供公共位置的日志收集，加快开发进度
- 对于问题排查：采用标准的日志格式，可根据类型快速过滤排查
- 对于项目预警：采用模块化+api，可自定操作

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
*备注：更多配置文件先hook 说明*

启动方式
- spring 启动：添加 @ComponentScan(basePackages={"io.promagent.agent"}) 
- 非spring 启动：new AgentBootstrap().init()

