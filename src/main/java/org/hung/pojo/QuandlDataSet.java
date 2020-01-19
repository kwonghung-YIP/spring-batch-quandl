package org.hung.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuandlDataSet<T> {
	
	private DataSet<T> dataset;
	
	@Data
	public static class DataSet<T> {
	
		@JsonProperty("dataset_code")
		private String datasetCode;
		
		@JsonProperty("database_code")
		private String databaseCode;
		
		private String name;
		
		@JsonProperty("description")
		private String desc;
		
		@JsonProperty("refreshed_at")
		@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		private LocalDateTime refreshAt;
		
		@JsonProperty("column_names")
		private String[] columnNames;
		
		private String frequency;
		
		private String type;
		
		@JsonProperty("start_date")
		private LocalDate startDate;
		
		@JsonProperty("end_date")
		private LocalDate endDate;
		
		private T[] data;
		
	}
	
}
