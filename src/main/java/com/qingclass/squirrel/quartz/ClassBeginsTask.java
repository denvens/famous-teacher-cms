package com.qingclass.squirrel.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.stereotype.Service;

@Service
public class ClassBeginsTask implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


    }



}
