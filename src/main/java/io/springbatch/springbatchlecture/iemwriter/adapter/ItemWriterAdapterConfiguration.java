package io.springbatch.springbatchlecture.iemwriter.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ItemWriterAdapterConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemWriterAdapterJob() {
        return jobBuilderFactory.get("itemWriterAdapterJob")
                .incrementer(new RunIdIncrementer())
                .start(itemWriterAdapterStep())
                .build();
    }

    @Bean
    public Step itemWriterAdapterStep() {
        return stepBuilderFactory.get("itemWriterAdapterStep")
                .<String, String>chunk(10)
                .reader(new ItemReader<String>() {
                    int i = 0;

                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 10 ? null : "item" + i;
                    }
                })
                .writer(customItemWriter())
                .build();
    }

    private ItemWriter<? super String> customItemWriter() {
        ItemWriterAdapter<String> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(customerService());
        writer.setTargetMethod("customWrite");

        return writer;
    }

    private CustomService<String> customerService() {
        return new CustomService<>();
    }
}
