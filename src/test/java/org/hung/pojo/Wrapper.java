package org.hung.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Wrapper<T> {

	private List<T> data;
	
}
