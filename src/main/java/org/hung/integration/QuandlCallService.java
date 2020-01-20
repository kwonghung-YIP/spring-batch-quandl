package org.hung.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuandlCallService {

	public List<String> distributeQuandlRequest() {
		return Arrays.asList("A","B","C");
	}
	
	public String callQuandlApi(String request) {
		return request;
	}
	
	public String consolidRequest(Collection<Message<String>> messages) {
		return "done";
	}
}
