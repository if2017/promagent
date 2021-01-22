hook配置

| key                                   | 说明                                                         | 默认值                       |
| :------------------------------------ | ------------------------------------------------------------ | ---------------------------- |
| `promagent.fastHooks.scheduledPack`   | 默认收集定时任务日志`收集该包下的@scheduled`                 | null                         |
| `promagent.agent.appName`               | 项目名                                                       | appName                      |
| `promagent.agent.callClass`             | 回调class                                                    | `com.cyou.agent.core.Logger` |
| `promagent.agent.debug`                 | classLoader 调试                                             | false                        |
| `promagent.agent.headers`             | 收集headers，all：收集全部header，none：不收集，headerA:headerB:headerC：收集header列表 | `Arrays.asList<"none">`        |
| `promagent.agent.ignoreSignatures`      | 不收集方法集合日志                                           | `new ArrayList<String>`        |
| `promagent.agent.mdcLogId`            | 传递到Mdc 中的 LogId                                         | access_id                    |
| `promagent.agent.retMaxLength`        | 收集ret 最大长度                                             | 默认20480                    |
| `promagent.agent.skipRetSignatures`   | 不收集放回的值的方法签名集合                                 | `new ArrayList<String>`        |
| `promagent.agent.traceId`             | 传递到其他项目的LogId                                        | X-REQUEST-ID                 |
| `promagent.fastHooks.controllerPack ` | 默认收集controller日志，收集该包下的@RequestMapping、@PostMapping、@GetMapping、@DeleteMapping、@PutMapping 日志 | null                         |
| `promagent.fastHooks.scheduledPack`   | 默认收集定时任务日志，收集该包下的@scheduled                 | null                         |
| `promagent.hooks.annClassHook`          | 类注解回调@Controller                                        | `new HashMap<String,List<String>>`               |
| `promagent.hooks.annMethodHook`         | 方法注解回调Hook 例如：@RequestMapping                       | `new HashMap<String,List<String>>`               |
| `promagent.hooks.annRegHook`            | 正则回调，详细例子见 com.cyou:log-agent-load中的 hook.yml 配置文件 | `new HashMap<String,List<String>>`               |
| `promagent.load.agentJar`               | 加载agentJar 路径                                            | null                         |
| `promagent.load.result`                 | 加载结果，true：加载成功，false：加载失败                    | false                        |
| `promagent.load.time`                   | 加载agent 花费时间                                           | -1                           |



正则收集例子
```

annMethodHook:                                                                      方法注解日志打印
      "^com.bb.*": !!seq                                                            正则表达式:^com.bb.*的package
           - "org.springframework.web.bind.annotation.RequestMapping:ACCESS"        日志打印 @RequestMapping 修饰的方法
           - "org.springframework.web.bind.annotation.PostMapping:ACCESS"           日志打印 @PostMapping 修饰的方法
           - "org.springframework.web.bind.annotation.GetMapping:ACCESS"            日志打印 @GetMapping 修饰的方法
           - "org.springframework.web.bind.annotation.DeleteMapping:ACCESS"         日志打印 @DeleteMapping 修饰的方法
           - "org.springframework.web.bind.annotation.PutMapping:ACCESS"            日志打印 @PutMapping 修饰的方法
           - "org.springframework.scheduling.annotation.Scheduled:CRON"             日志打印 @Scheduled 修饰的方法

annClassHook:                                                                       类注解日志打印
      "^com.bb.*": !!seq                                                            正则表达式:^com.bb.*的package
           - "org.springframework.web.bind.annotation.RestController:ACCESS"        日志打印 @RestController 修饰的方法
regHook:                                                                            正则日志打印
      "^com.bb.bbfff.code.util.HttpUtils$:THIRD": !!seq                             正则表达式:^com.bb.bbfff.code.util.HttpUtils$ 的package，日志类型为 THIRD，默认为SYSTEM
           - ".*post.*"                                                             正则表达式：.*post.* 的所有方法
      "^com.bb.bbfff.code.plpay.common.BaasHttpUtil$:THIRD": !!seq
           - "[a-zA-Z].*"                                                           正则表达式：[a-zA-Z].* 的所有方法，不能直接使用.*,需要加限定词[a-zA-Z].*
      "^com.bb.common.util.HttpClientProxy$:THIRD": !!seq
           - "[a-zA-Z].*:1"                                                         正则表达式：[a-zA-Z].*:1 的所有方法，方法参数个数为1
      "^com.bb.openplatform.http.HttpUtil$:THIRD": !!seq
           - ".*Response.*"                                                         正则表达式：.*Response.* 的所有方法
```

