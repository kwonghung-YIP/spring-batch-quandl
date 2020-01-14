package org.hung.pojo.database;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonFormat(shape = Shape.ARRAY)
@JsonPropertyOrder({"date","nominalPrice","netChange","changePct","bid","ask","pe","high","low","prevClose","shareVolume","turnover","lotSize"})
public class HKEX {

	private LocalDate date;
	
	private Double nominalPrice;
	
	private Double netChange;
	
	private Double changePct;
	
	private Double bid;
	
	private Double ask;
	
	private Double pe;
	
	private Double high;
	
	private Double low;
	
	private Double prevClose;
	
	private Double shareVolume;
	
	private Double turnover;
	
	private Double lotSize;
	
}
