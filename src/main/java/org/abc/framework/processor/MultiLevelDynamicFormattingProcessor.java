/******************************************************************************
 * 
 * File Name : MultiLevelDynamicFormattingProcessor.java 
 * Description : Implements the abstract process method for formatting processing.
 * 
 * @author : TCS Date : 04/3/2012 07:00:00 PM
 * @version : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;


/**
 * @author : TCS
 * @version : 1.0
 */
public class MultiLevelDynamicFormattingProcessor extends AbstractFormattingProcessor
{
	/**
	 * This method calls the method addLeveledDataToList as per the levels
	 * available in AttributesBean.
	 * 
	 * @param object_
	 *            Object
	 * @return List Returns the List of data to be write to the file.
	 */
	public List<String> process(final Object object_)
	{
		final AttributesBean attributesBean = (AttributesBean) object_;
		final List<String> dataList = new ArrayList<String>();
		Map<String, String> dataMap1 = null;
		
		List<AttributesBean> varryingFields1 = null;

		dataMap1 = attributesBean.getAttributes();
		final boolean newLineAdded = true;
		setTotalRecordCount(getTotalRecordCount() + 1);
		addLeveledDataToList(dataMap1, dataList, 1, newLineAdded);

		varryingFields1 = attributesBean.getVarryingFields();
		for (int loopLevel1 = 0; loopLevel1 < varryingFields1.size(); loopLevel1++)
		{
			prepareDataList(dataList, varryingFields1, loopLevel1, 2);
		}
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
	/**
	 * Prepare Data list for for current level.
	 * Calls itself for next levels.
	 * 
	 * @param dataList_ List
	 * @param currentlLevelVarryingFields_ List
	 * @param loopCounter_ int 
	 * @param recordLevel_ int
	 */
	private void prepareDataList(final List<String> dataList_, final List<AttributesBean> currentlLevelVarryingFields_,
			final int loopCounter_, final int recordLevel_)
	{
		final boolean newLineAdded = addNewLine(loopCounter_, dataList_, recordLevel_);
		final Map<String, String> currentDataMap = currentlLevelVarryingFields_.get(loopCounter_).getAttributes();
		addLeveledDataToList(currentDataMap, dataList_, recordLevel_, newLineAdded);
		final List<AttributesBean> nextLevelVarryingFields = currentlLevelVarryingFields_.get(loopCounter_)
				.getVarryingFields();
		final int nextLevel = recordLevel_ + 1;
		for (int nextLevelLoopCounter = 0; nextLevelLoopCounter < nextLevelVarryingFields
				.size(); nextLevelLoopCounter++)
		{
			prepareDataList(dataList_, nextLevelVarryingFields, nextLevelLoopCounter, nextLevel);
		}
	}

}
