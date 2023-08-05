package io.springbatch.springbatchlecture.controller;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping(path = "/run")
    public ExitStatus runJob(@RequestBody JobLauncherRequest request) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Job job = this.applicationContext.getBean(request.getName(), Job.class);

        return this.jobLauncher.run(job, request.getJobParameters()).getExitStatus();
    }
}
