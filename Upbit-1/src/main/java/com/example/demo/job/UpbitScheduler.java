package com.example.demo.job;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling // 스케쥴러 기능 활성화
@RequiredArgsConstructor
@Component
public class UpbitScheduler {
	
	 @Autowired
	 private JobLauncher jobLauncher;
	
	@Autowired
	 private BatchConfig jobconfig;

	 // Job Scheduler 60초마다 배치실행
    @Scheduled(fixedDelay = 10000)
    //                 초 분 시 월 년 요일
    @Scheduled(cron = "05 0 * * * ?")
    public void runJob() {
    	
    	 	Map<String, JobParameter> confMap = new HashMap<>();
	        confMap.put("time", new JobParameter(System.currentTimeMillis()));
	        JobParameters jobParameters = new JobParameters(confMap);
    	
    	
    	   
	            try {
				
	            	jobLauncher.run(jobconfig.job(), jobParameters);
				
	            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
						| JobParametersInvalidException e) {
					e.printStackTrace();
				}
	          

	       
    	
    	
    }
	
	
}
