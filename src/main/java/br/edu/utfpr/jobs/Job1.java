/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1 implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job1 --->>> Hello geeks! Time is " + new Date());
    }
}
