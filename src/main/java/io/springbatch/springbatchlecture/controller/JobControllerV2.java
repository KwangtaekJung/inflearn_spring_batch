package io.springbatch.springbatchlecture.controller;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Set;

@RestController
public class JobControllerV2 {

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private JobExplorer jobExplorer;

    @PostMapping(value = "/batch/start")
    public String start(@RequestBody JobLauncherRequest request) throws Exception {

//        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
//
//            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
//            Job job = jobRegistry.getJob(iterator.next());
//            System.out.println("job name: " + job.getName());
//
//            jobOperator.start(job.getName(), "id=" + jobInfo.getId());
//        }
        jobOperator.start(request.getName(), "startDate=" + request.getStartDate());

        return String.format("batch(%s)[%s] is started", request.getName(), request.getStartDate());
    }

    @PostMapping(value = "/batch/restart")
    public String restart(@RequestBody JobLauncherRequest request) throws Exception {

//        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {

//            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
//            System.out.println("job name: " + job.getName());

//        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(request.getName());
//        JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);
//        jobOperator.restart(lastJobExecution.getId());

//        }

        JobExecution jobExecution = jobExplorer.getJobExecution(request.getJobExecutionId());
        String jobName = jobExecution != null ? jobExecution.getJobInstance().getJobName() : null;

        jobOperator.restart(request.getJobExecutionId());

        return String.format("batch(%s)[%d] is restarted", jobName, request.getJobExecutionId());
    }

    @PostMapping(value = "/batch/stop")
    public String stop() throws Exception {

        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {

            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("job name: " + job.getName());

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            JobExecution jobExecution = runningJobExecutions.iterator().next();

            jobOperator.stop(jobExecution.getId());
        }

        return "batch is stopped";
    }

    @PostMapping(value = "/batch/monitor")
    public String monitor(@RequestBody JobLauncherRequest request) throws NoSuchJobExecutionException {

        return jobOperator.getSummary(request.getJobExecutionId());
    }
}