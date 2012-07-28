package org.abc.framework.mapper;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DynamicPatternMatchingCompositeLineMapper_Delimited.xml")
public class DynamicPatternMatchingCompositeLineMapperDelimitedTest extends TestCase
{
	@Autowired
	protected Job job;

	@Autowired
	protected JobLauncher jobLauncher;
	
	@Test
	public void dynamicLineMapperTest() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobExecution exec = jobLauncher.run(job,new JobParametersBuilder().toJobParameters());
	}

}
