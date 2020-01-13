package org.hung.config;



import org.hung.batch.QuandlDatasetItemReader;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@ImportResource("simple-job-config.xml")
public class BatchConfig {

	@Bean
	//@StepScope
	public ItemReader<String> reader(ObjectMapper mapper, RestTemplateBuilder builder) {
		return new QuandlDatasetItemReader<String>(mapper, builder);
	}
	
	@Bean
	@StepScope
	public ItemProcessor<String,String> filter() {
		return item -> {
			return null;
		};
	}
	
	@Bean
	@StepScope
	public ItemWriter<String> writer() {
		return list -> {
			
		};
	}
}
