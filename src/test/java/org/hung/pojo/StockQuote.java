package org.hung.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
@JsonFormat(shape = Shape.ARRAY)
public class StockQuote {

	private String date;
	
	private Double open;
	
	private Double close;
	
	private Double high;
	
	private Double low;
	
}
