package io.springbatch.springbatchlecture.itemreader.flatfile;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class FlatFileFixedLengthConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileFixedLengthReaderJob() {
        return jobBuilderFactory.get("flatFileFixedLengthReaderJob")
                .start(step1())
                .next(step2())
                .build();
    }

    //    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(itemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        items.forEach(item -> System.out.println(item));
                    }
                })
                .build();
    }

    //    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    //    @Bean
    public ItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new FileSystemResource("C:\\WorkSpace\\study\\inflearn\\springbatchlecture\\src\\main\\resources\\customer.txt"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .strict(false)
                .addColumns(new Range(1, 5))
                .addColumns(new Range(6, 9))
                .addColumns(new Range(10, 11))
                .names("name", "year", "age")
                .build();
    }
}
