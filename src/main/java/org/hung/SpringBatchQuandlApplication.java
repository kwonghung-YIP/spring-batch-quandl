package org.hung;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBatchQuandlApplication {

	public static void main(String[] args) {
		
		ApplicationContext appCtx = SpringApplication.run(SpringBatchQuandlApplication.class, args);
		
		JobLauncher joblauncher = appCtx.getBean(JobLauncher.class);
		
		Job callQuandlApi = appCtx.getBean("call-quandl-api",Job.class);
		
		JobParameters ckh = new JobParametersBuilder()
				.addString("database-code", "HKEX")
				.addString("dataset-code", "00001")
				.toJobParameters();
		
		JobParameters clp = new JobParametersBuilder()
				.addString("database-code", "HKEX")
				.addString("dataset-code", "00002")
				.toJobParameters();
		
		JobParameters hsbc = new JobParametersBuilder()
				.addString("database-code", "HKEX")
				.addString("dataset-code", "00005")
				.toJobParameters();
		
		//joblauncher.run(job1, jobParameters);
		//joblauncher.run(job2, jobParameters);
		try {
			joblauncher.run(callQuandlApi, ckh);
			joblauncher.run(callQuandlApi, clp);
			joblauncher.run(callQuandlApi, hsbc);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
