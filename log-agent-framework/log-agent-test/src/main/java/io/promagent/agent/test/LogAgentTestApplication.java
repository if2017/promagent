package io.promagent.agent.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages={"io.promagent"})
@SpringBootApplication
@EnableScheduling
public class LogAgentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogAgentTestApplication.class, args);
	}

}
