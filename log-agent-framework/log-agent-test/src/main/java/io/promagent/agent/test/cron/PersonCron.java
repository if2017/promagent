package io.promagent.agent.test.cron;

import com.ejlchina.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zhangyi
 * @desc:
 * @date: 2021/6/19
 */
@Component
public class PersonCron {

    @Resource
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Scheduled(fixedRate = 5000)
    public void getPeopleInfo() {
        String result = OkHttps.sync("http://127.0.0.1:" +  webServerAppCtxt.getWebServer().getPort() + "/people/12")
                .addHeader("sign", "sign")
                .get()
                .getBody()
                .toString();
        System.out.println(result);
    }

    @Scheduled(fixedRate = 5000)
    public void getException() {
        String result = OkHttps.sync("http://127.0.0.1:" +  webServerAppCtxt.getWebServer().getPort() + "/people/getException")
                .addHeader("sign", "sign")
                .get()
                .getBody()
                .toString();
        System.out.println(result);
    }

    @Scheduled(fixedRate = 5000)
    public void testParams() {
        String result = OkHttps.sync("http://127.0.0.1:" +  webServerAppCtxt.getWebServer().getPort() + "/people/12?name=name&age=12")
                .addHeader("sign", "sign")
                .get()
                .getBody()
                .toString();
        System.out.println(result);
    }

}
