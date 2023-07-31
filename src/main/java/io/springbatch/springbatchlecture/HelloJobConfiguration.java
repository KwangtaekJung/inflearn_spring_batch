package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
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

@RequiredArgsConstructor
//@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1())
                .next(helloStep2())
                .next(helloStep3())
//                .incrementer(new CustomJobParametersIncrementer())
                .build();
    }

    private Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> Step1");
//                        JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
//                        System.out.println(jobParameters.getString("name"));
//                        System.out.println(jobParameters.getDate("date"));
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    private Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                        chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.COMPLETED);
//                        stepContribution.setExitStatus(ExitStatus.FAILED);
                        System.out.println(">> Step2");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    private Step helloStep3() {
        return stepBuilderFactory.get("helloStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    System.out.println(">> Step3");
                    return RepeatStatus.FINISHED;
                })
                .startLimit(3)
                .build();
    }

}
