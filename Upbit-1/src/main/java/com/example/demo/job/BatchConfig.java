package com.example.demo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
	
	
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private static final int chunkSize = 10;
	
	 @Bean
	    public Job job(){
	        return jobBuilderFactory.get("job")
	                .incrementer(new RunIdIncrementer())
	                .start(this.step1())
	                .build();
	    }

	    @Bean
	    public Step step1(){
	        return stepBuilderFactory.get("step1")
	        	            .tasklet(new OuterTasklet())
	        	            .build();
	    }

	   
	  
	    
	
	
	
	

}
