package org.hung.batch;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.hung.pojo.QuandlDataSet;
import org.hung.pojo.database.HKEX;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuandlDatasetItemReader<T> implements ItemStreamReader<T> {
	
	private ObjectMapper mapper;
	
	private RestTemplate restTemplate;
	
	@Value("${quandl-apikey}")
	private String apiKey;
	
	//private List<String> dummy = new ArrayList<String>(Arrays.asList("A","B","C"));
	
	private int currentIndex = 0;
	
	private QuandlDataSet<T> dataset;
	
	public QuandlDatasetItemReader(ObjectMapper mapper, RestTemplateBuilder builder) {
		this.mapper = mapper;
		this.restTemplate = builder
				.build();
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		log.info("open");
		
		/*JavaType javaType = mapper.getTypeFactory().constructParametricType(QuandlDataSet.class,HKEX.class);
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
		
		restTemplate.setMessageConverters(Arrays.asList(converter));*/
		
		URI uri =  UriComponentsBuilder
				.fromHttpUrl("https://www.quandl.com/api/v3/datasets/{database}/{stockCode}.json")
				.queryParam("api_key", this.apiKey)
				.queryParam("start_date", "2018-01-01")
				.build("HKEX","00005");
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
		
		ParameterizedTypeReference<QuandlDataSet<HKEX>> paramTypeRef = new ParameterizedTypeReference<QuandlDataSet<HKEX>>(){};
		
		ResponseEntity<QuandlDataSet<HKEX>> responseEntity = restTemplate.exchange(requestEntity, paramTypeRef);
		
		this.dataset = (QuandlDataSet<T>)responseEntity.getBody();
		
		currentIndex = 0;
		executionContext.put("currentIndex", currentIndex);
		
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		log.info("update");
		executionContext.put("currentIndex", currentIndex);
		
	}

	@Override
	public void close() throws ItemStreamException {
		log.info("close");
		// TODO Auto-generated method stub
		
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		//log.info("read");

		if (currentIndex < dataset.getDataset().getData().length) {
			Object item = dataset.getDataset().getData()[currentIndex++];
			log.info("{}",item);
			return (T)item;
		}
		return null;
	}
	
	@AfterRead
	public void afterRead(T item) {
		log.info("AfterRead:{}",item);
	}
	

}
