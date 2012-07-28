package org.abc.framework.reader;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("AttributeBeanJdbcCursorPeekableItemReader.xml")
public class AttributeBeanJdbcCursorPeekableItemReaderTest extends TestCase
{

	@Autowired
	protected Job job;

	@Autowired
	protected JobLauncher jobLauncher;
	
	/**
	 * @throws Exception
	 */
	@Test
	public void existingServiceJobPass() throws Exception
	{
		
		JobExecution exec = jobLauncher.run(job,new JobParametersBuilder().toJobParameters());
		
		
	}

}
