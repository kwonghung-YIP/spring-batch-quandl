package org.hung;

import org.hung.pojo.Person;
import org.hung.pojo.StockQuote;
import org.hung.pojo.Wrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.FileSystemResource;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonTest
class SpringBatchQuandlApplicationTests {

	@Test
	void testPerson() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JavaType javaType = TypeFactory.defaultInstance().constructParametricType(Wrapper.class,Person.class);
		
		FileSystemResource resource = new FileSystemResource("src/test/resources/person.json");
		
		Wrapper<Person> wrapper = mapper.readValue(resource.getFile(),javaType);
		
		log.info("{}",wrapper);

	}

	@Test
	void testStockQuote() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JavaType javaType = TypeFactory.defaultInstance().constructParametricType(Wrapper.class,StockQuote.class);
		
		FileSystemResource resource = new FileSystemResource("src/test/resources/stock-quote.json");
		
		Wrapper<StockQuote> wrapper = mapper.readValue(resource.getFile(),javaType);
		
		log.info("{}",wrapper);

	}
	
}
