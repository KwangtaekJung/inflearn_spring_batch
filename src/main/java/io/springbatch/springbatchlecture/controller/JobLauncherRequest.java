package io.springbatch.springbatchlecture.controller;

import lombok.Data;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.util.Properties;

@Data
public class JobLauncherRequest {

    private Long jobExecutionId;
    private String name;
    private String startDate;
    private Properties jobParameters;

    public JobParameters getJobParameters() {
        Properties properties = new Properties();
        properties.putAll(this.jobParameters);
        return new JobParametersBuilder(properties)
                .toJobParameters();
    }
}
