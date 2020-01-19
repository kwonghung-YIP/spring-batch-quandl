package org.hung.batch;

import java.net.URI;
import java.util.List;

import org.hung.pojo.QuandlDataSet;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuandlDatasetItemReader<T> implements ItemStreamReader<T> {
	
	private RestTemplate restTemplate;
	
	@Value("${quandl-apikey}")
	private String apiKey;
	
	private int currentIndex = 0;
	
	private Class<T> datasetType;
	
	@Setter
	private String databaseCode;
	@Setter
	private String datasetCode;
	
	private QuandlDataSet<T> dataset;
	
	public QuandlDatasetItemReader(RestTemplateBuilder builder, Class<T> datasetType) {
		this.restTemplate = builder
				.build();
		this.datasetType = datasetType;
	} 

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		log.info("open");
		
		URI uri =  UriComponentsBuilder
				.fromHttpUrl("https://www.quandl.com/api/v3/datasets/{databaseCode}/{datasetCode}.json")
				.queryParam("api_key", this.apiKey)
				.queryParam("start_date", "2018-01-01")
				.build(this.databaseCode, this.datasetCode);
				//.build("HKEX","00005");
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
		
		//ParameterizedTypeReference<QuandlDataSet<HKEX>> paramTypeRef = new ParameterizedTypeReference<QuandlDataSet<HKEX>>(){};
		
		JavaType javaType = TypeFactory.defaultInstance().constructParametricType(QuandlDataSet.class, this.datasetType);
		
		ParameterizedTypeReference<QuandlDataSet<T>> paramTypeRef = ParameterizedTypeReference.forType(javaType);
		
		ResponseEntity<QuandlDataSet<T>> responseEntity = restTemplate.exchange(requestEntity, paramTypeRef);
		
		this.dataset = responseEntity.getBody();
		
		currentIndex = 0;
		executionContext.put("dataset",dataset);
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
			//log.info("{}",item);
			return (T)item;
		}
		return null;
	}
	
	@AfterRead
	public void afterRead(T item) {
		//log.info("AfterRead:{}",item);
	}
	
	@AfterWrite
	public void afterWrite(List<T> items) {
		//log.info("{}",items);
	}
	

}
