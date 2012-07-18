/******************************************************************************
 *
 * File Name        : DynamicFormattingProcessor.java
 * Description      : Calls the method for formatting data acquired from the file.  
 * Author           : TCS
 * Date             : 26/3/2012 07:00:00 PM
 * Version          : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

/**
 * @author TCS
 * @version 1.0
 * 
 */
public class DynamicFormattingProcessor extends AbstractFormattingProcessor
{
	private boolean isDelimitedFile;
	
	/**
	 * @return the isDelimitedFile
	 */
	public boolean getIsDelimitedFile()
	{
		return isDelimitedFile;
	}

	/**
	 * @param isDelimitedFile_ the isDelimitedFile to set
	 */
	public void setIsDelimitedFile(final boolean isDelimitedFile_)
	{
		this.isDelimitedFile = isDelimitedFile_;
	}
	/**
	 * This method calls the method for formatting data if formatting flag is
	 * active or if there are fillers in the data.
	 * 
	 * @param object_
	 *            Object
	 * @return List Returns the List of formatted data.
	 */

	@Override
	public List<String> process(final Object object_)
	{
		final Map<String, String> dataMap = (Map<String, String>) object_;
		final List<String> dataList = new ArrayList<String>();
		final boolean newLineAdded = true;
		// Length formatting is not required because its done using xml
		// FormatterLineAggregator
		setApplyLenghFormatting(false);
		
		// Excess Data truncation is not required because its Delimited by pipe
		// DelimitedLineAggregator
		if(isDelimitedFile)
		{
			setTruncateExcessData(false);
		}
		// This class is used for only level 1 records
		setTotalRecordCount(getTotalRecordCount() + 1);
		addLeveledDataToList(dataMap, dataList, 1, newLineAdded);

		return dataList;
	}
	
	/**
	 * This method is overrided to set NOOP in case of 0 records.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 * @return ExitStatus ExitStatus
	 */
	@Override
	protected ExitStatus getExitStatus(final StepExecution stepExecution_)
	{
		if(getTotalRecordCount() == 0)
		{
			return ExitStatus.NOOP;
		}
		else
		{
			return ExitStatus.COMPLETED;
		}
	}
	
	

}
