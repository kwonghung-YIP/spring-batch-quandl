package org.hung.config;

import org.hung.integration.QuandlCallService;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
@ImportResource("integration-config.xml")
public class IntegrationConfig {

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		return new TaskExecutorBuilder()
				.corePoolSize(10)
				.maxPoolSize(20)
				.build();
	}
	
	@Bean
	public QuandlCallService quandlCallService() {
		QuandlCallService service = new QuandlCallService(); 
		return service;
	}
	
}
