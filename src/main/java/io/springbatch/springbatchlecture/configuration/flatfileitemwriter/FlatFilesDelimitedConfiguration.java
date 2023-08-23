package io.springbatch.springbatchlecture.configuration.flatfileitemwriter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlatFilesDelimitedConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFilesDelimitedJob() {
        return jobBuilderFactory.get("flatFilesDelimitedWriterJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(10)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    public ItemWriter<? super Customer> customerItemWriter() {

        return new FlatFileItemWriterBuilder<>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("C:\\WorkSpace\\study\\inflearn\\springbatchlecture\\src\\main\\resources\\flatFileCustomer.txt"))
                .append(true)
                .delimited()
                .delimiter("|")
                .names(new String[]{"id", "name", "age"})
                .build();
    }

    public ItemReader<? extends Customer> customerItemReader() {

        List<Customer> customers = Arrays.asList(
                new Customer(1, "hong gil dong1", 41),
                new Customer(2, "hong gil dong2", 42),
                new Customer(3, "hong gil dong3", 43)
        );

        ListItemReader<Customer> reader = new ListItemReader<>(customers);

        return reader;
    }
}