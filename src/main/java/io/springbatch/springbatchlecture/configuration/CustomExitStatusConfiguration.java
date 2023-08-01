package io.springbatch.springbatchlecture.configuration;

import io.springbatch.springbatchlecture.util.PassCheckingListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CustomExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .on("FAILED")
                .to(step2())
                .on("PASS")
                .stop()
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> Step1");
//                        JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
//                        System.out.println(jobParameters.getString("name"));
//                        System.out.println(jobParameters.getDate("date"));

                        stepContribution.setExitStatus(ExitStatus.FAILED);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("Step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                        chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.COMPLETED);
//                        stepContribution.setExitStatus(ExitStatus.FAILED);
                        System.out.println(">> Step2");
                        return RepeatStatus.FINISHED;
                    }
                })
                .listener(new PassCheckingListener())
                .build();
    }

}
