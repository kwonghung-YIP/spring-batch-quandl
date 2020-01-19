package org.hung.config;



import java.util.Map;

import javax.sql.DataSource;

import org.hung.batch.QuandlDatasetItemReader;
import org.hung.pojo.QuandlDataSet;
import org.hung.pojo.database.HKEX;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.GenericTypeResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@ImportResource("simple-job-config.xml")
public class BatchConfig {

	@Autowired
	private RestTemplateBuilder builder;
	
	public abstract class QuandlDatasetConfig<T> {

		public QuandlDatasetItemReader<T> reader(Map jobParameters) {
			Class clazz = (Class<T>)GenericTypeResolver.resolveTypeArgument(getClass(),QuandlDatasetConfig.class);
			
			QuandlDatasetItemReader<T> reader = new QuandlDatasetItemReader<T>(builder, clazz);
			reader.setDatabaseCode((String)jobParameters.get("database-code"));
			reader.setDatasetCode((String)jobParameters.get("dataset-code"));
			return reader;
		}
		
		public ItemProcessor<T,String> filter() {
			return item -> {
				return null;
			};
		}
		
		public JdbcBatchItemWriter<T> writer(QuandlDataSet<T> dataset, DataSource dataSource) {
			return new JdbcBatchItemWriterBuilder<T>()
					.dataSource(dataSource)
					.beanMapped()
					.sql("insert into quandl_db_hkex ("
							+ "stock_code, trade_date, bid, ask, low, high, volume, turnover"
						+ ") values("
							+ "'" + dataset.getDataset().getDatasetCode() +"', :date, :bid, :ask, :low, :high, :shareVolume, :turnover"
						+ ")")
					.build();
		}		
	}
	
	@Configuration
	public class HkexConfig extends QuandlDatasetConfig<HKEX> {
		
		@Bean("hkex-reader")
		@StepScope
		public QuandlDatasetItemReader<HKEX> reader(@Value("#{jobParameters}") Map jobParameters) {
			return super.reader(jobParameters);
		}
			
		@Bean("hkex-writer")
		@StepScope
		public JdbcBatchItemWriter<HKEX> writer(@Value("#{stepExecution}") StepExecution stepExecution, DataSource dataSource) {
			QuandlDataSet<HKEX> dataset = (QuandlDataSet<HKEX>)stepExecution.getExecutionContext().get("dataset");
			return super.writer(dataset, dataSource);
		}
	}
	

}