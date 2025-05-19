package com.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.batch.model.Customer;

@Configuration
public class BatchConfig {
	
	@Bean
	public Job jobBean(JobRepository jobRepository,
            JobCompletionNotificationImpl listener,
            Step steps) {
		
		 return new JobBuilder("job", jobRepository)
	                .listener(listener)
	                .start(steps)
	                .build();
		
	}
	  @Bean
	    public Step steps(
	            JobRepository jobRepository,
	            DataSourceTransactionManager transactionManager,
	            ItemReader<Customer> reader,
	            ItemProcessor<Customer, Customer> processor,
	            ItemWriter<Customer> writer
	    ) {
	        return new StepBuilder("jobStep", jobRepository)
	                .<Customer,Customer>chunk(5, transactionManager)
	                .reader(reader)
	                .processor(processor)
	                .writer(writer)
	                .allowStartIfComplete(true)
	                .build();

	    }  
	  
	/*  @Bean
	    public FlatFileItemReader<Customer> reader() { 
	        return new FlatFileItemReaderBuilder<Customer>()
	                .name("itemReader")
	                .resource(new ClassPathResource("dataSource.txt"))
	                .linesToSkip(1)
	                .delimited()
	                .delimiter("|")
	                .names("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME","CUSTOMER_ID")
	                .targetType(Customer.class)
	                .build();
	    }*/
	  @Bean
	  public FlatFileItemReader<Customer> reader() {
	      FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
	      reader.setResource(new ClassPathResource("dataSource.txt"));
	      reader.setLinesToSkip(1); // skip header

	      DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

	      DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
	      tokenizer.setDelimiter("|");
	      tokenizer.setNames("accountNumber", "trxAmount", "description", "trxDate", "trx_time", "customerId");

	      lineMapper.setLineTokenizer(tokenizer);
	      lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());

	      reader.setLineMapper(lineMapper);
	      return reader;
	  }
	  @Bean
	    public ItemProcessor<Customer, Customer> itemProcessor() {
	        return new CustomItemProcessor();
	    }
	  
	  @Bean
	    public ItemWriter<Customer> itemWriter(DataSource dataSource) {

	        return new JdbcBatchItemWriterBuilder<Customer>()
	                .sql("insert into customers(account_number,trx_amount,description,trx_date,trx_time,customer_id)values(:accountNumber, :trxAmount, :description, :trxDate, :trx_time, :customerId)")
	                .dataSource(dataSource)
	                .beanMapped()
	                .build();

	    }

}
