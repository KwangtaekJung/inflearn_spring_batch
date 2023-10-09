package io.springbatch.springbatchlecture.listener.jobstep;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JobAndStepListenerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomStepExecutionListener customStepExecutionListener;

    @Bean
    public Job jobAndStepListenerJob() {
        return jobBuilderFactory.get("JobAndStepListenerJob")
                .incrementer(new RunIdIncrementer())
                .start(jobAndStepListenerStep1())
                .next(jobAndStepListenerStep2())
                .listener(new CustomJobExecutionListener())
//                .listener(new CustomAnnotationJobExecutionListener())
                .build();
    }

    @Bean
    public Step jobAndStepListenerStep1() {
        return stepBuilderFactory.get("jobAndStepListenerStep1")
                .tasklet(((stepContribution, chunkContext) -> RepeatStatus.FINISHED))
                .listener(customStepExecutionListener)
                .build();
    }

    @Bean
    public Step jobAndStepListenerStep2() {
        return stepBuilderFactory.get("jobAndStepListenerStep2")
                .tasklet(((stepContribution, chunkContext) -> RepeatStatus.FINISHED))
                .listener(customStepExecutionListener)
                .build();
    }

}
