package io.springbatch.springbatchlecture.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job repeatJob() {
        return jobBuilderFactory.get("myRepeatJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> Step1");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    final RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

//                        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));
//                        repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(3000));

                        CompositeCompletionPolicy compositeCompletionPolicy = new CompositeCompletionPolicy();
                        CompletionPolicy[] completionPolicies = new CompletionPolicy[]{
                                new SimpleCompletionPolicy(3),
                                new TimeoutTerminationPolicy(1000)};
                        compositeCompletionPolicy.setPolicies(completionPolicies);
                        repeatTemplate.setCompletionPolicy(compositeCompletionPolicy);

                        repeatTemplate.iterate(new RepeatCallback() {
                            @Override
                            public RepeatStatus doInIteration(RepeatContext repeatContext) throws Exception {
                                System.out.println("RepeatTemplate-CompletionPolicy");
                                return RepeatStatus.CONTINUABLE;
                            }
                        });
                        System.out.println(">> Step2 is ended");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    final RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                        repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler());

                        repeatTemplate.iterate(new RepeatCallback() {
                            @Override
                            public RepeatStatus doInIteration(RepeatContext repeatContext) throws Exception {
                                System.out.println("RepeatTemplate-ExceptionHandler");
                                throw new RuntimeException("Exception is occurred.");
//                            return RepeatStatus.CONTINUABLE;
                            }
                        });

                        System.out.println(">> Step3");
                        return RepeatStatus.FINISHED;
                    }

                })
                .build();
    }

    @Bean
    public ExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }
}
