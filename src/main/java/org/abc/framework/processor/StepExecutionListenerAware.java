/******************************************************************************
 *
 * File Name        : StepExecutionListenerAware.java
 * Description      : This is an interface for getting and setting the Execution context
 * 						of the job under execution
 * Author           : 
 * Date             : 05/05/2012 07:00:00 PM
 * Version          : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.processor;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

/***
 * 
 * @author 
 * @version 1.0
 *
 */
public interface StepExecutionListenerAware 
{
		/***
		 * 
		 * @return StepExecution
		 */
		StepExecution getStepExecution();
		/***
		 * 
		 * @param stepExecution_ StepExecution
		 */
		void setStepExecution(final StepExecution stepExecution_);
		
		/**
		 * This method is called after the Read Write step is completes execution.
		 * 
		 * @param stepExecution_
		 *            StepExecution
		 * @return ExitStatus ExitStatus
		 */
		ExitStatus afterStep(final StepExecution stepExecution_);
}
