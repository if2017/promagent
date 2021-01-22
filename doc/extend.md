扩展：如果当前插点不满足你项目需求，可根据下列文档扩展模块

注解

```
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
```

callbackMethodName和callbackFrameErrorMethodName传递参数类型

| 参数名      | 参数类型  | 含义     |
| ----------- | --------- | -------- |
| executeTime | String    | 执行时间 |
| throwable   | Throwable | 方法异常 |
| ret         | Object    | 返回的值 |
| method      | Method    | 执行方法 |
| arguments   | Object[]  | 方法参数 |

