package org.hung.pojo.database;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HKEX {

	private LocalDate date;
	
	private Float nominalPrice;
	
	private Float netChange;
	
	private Float changePct;
	
	private Float bid;
	
	private Float ask;
	
	private Float pe;
	
	private Float high;
	
	private Float low;
	
	private Float prevClose;
	
	private Float shareVolume;
	
	private Float turnover;
	
	private Float lotSize;
	
}
