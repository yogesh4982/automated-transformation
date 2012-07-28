package org.abc.framework.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.blo.InterfaceMasterBLO;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Required;

/******************************************************************************
 * 
 * File Name : InterfaceMasterReaderTasklet.java Description : Gets the data
 * from the database and put it into Job Execution Context.
 * 
 * @author :  
 * @Date : 29/3/2012 11:40:00 AM
 * @version : 1.0
 * 
 *****************************************************************************/
public class InterfaceMasterReaderTasklet implements Tasklet
{

	private InterfaceMasterBLO interfaceMasterBLO;

	private String interfaceName;

	/**
	 * This method is used to get the data from the database and put it into Job
	 * Execution Context.
	 * 
	 * @param contribution_
	 *            StepContribution
	 * @param chunkContext_
	 *            ChunkContext
	 * @return RepeatStatus
	 * @throws Exception
	 *             exception
	 */
	public RepeatStatus execute(final StepContribution contribution_,final ChunkContext chunkContext_) throws Exception
	{
		// Get the interface name mentioned in the job name 
		interfaceName = chunkContext_.getStepContext().getJobName();
		final ExecutionContext jobExecutionContext = chunkContext_.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
		final Map<String, String> interfaceMaster = interfaceMasterBLO.
														getInterfaceConfigurationForJobName(interfaceName);
		// Put values in the interface_master table to jobexecution context.
		for (String key : interfaceMaster.keySet())
		{
			jobExecutionContext.putString(key, interfaceMaster.get(key));
		}
		
		// Get details of interface from interface_configuration table
		final List<InterfaceConfigurationBean> interfaceConfigurationBeanList = interfaceMasterBLO
				.getInterfaceConfigurationBeanList(Integer.parseInt(interfaceMaster.get("INTERFACE_ID")));
		// Put configuration in jobexecution context.
		jobExecutionContext.put("interfaceConfigurationBeanList", interfaceConfigurationBeanList);
		
		
		final List<Map<String, String>> maximumRecordLevelMap = interfaceMasterBLO
				.getInterfaceConfigurationMaximumLevel(Integer.parseInt(interfaceMaster.get("INTERFACE_ID")));

		
		// Put max record level in jobexecution context
		if(maximumRecordLevelMap != null && 
				maximumRecordLevelMap.get(0).containsKey("MAX_RECORD_LEVEL"))
		{
			jobExecutionContext.put("MAX_RECORD_LEVEL", 
					maximumRecordLevelMap.get(0).get("MAX_RECORD_LEVEL"));
		}
		
		
		final Map<String, String> sourceColumnToFieldIdMap = new HashMap<String, String>();
		final Map<String, String> fieldIdToFieldNameMap = new HashMap<String, String>();
		final Map<String, InterfaceConfigurationBean> fieldIdToConfigBean = new HashMap<String, InterfaceConfigurationBean>();
		for(InterfaceConfigurationBean configBean: interfaceConfigurationBeanList)
		{
			if (!configBean.getFieldName().equalsIgnoreCase(Constants.FILLER))
			{
				sourceColumnToFieldIdMap.put(configBean.getSourceColumn().toUpperCase().trim()+
						Constants.UNDER_SCORE+configBean.getRecordLevel(), 
						configBean.getFieldId());
				fieldIdToFieldNameMap.put(configBean.getFieldId(), configBean.getFieldName());
			}
			fieldIdToConfigBean.put(configBean.getFieldId(), configBean);
		}
		jobExecutionContext.put("sourceColumnToFieldIdMap", sourceColumnToFieldIdMap);
		jobExecutionContext.put("fieldIdToFieldNameMap", fieldIdToFieldNameMap);
		jobExecutionContext.put("fieldIdToConfigBean", fieldIdToConfigBean);
		
		return RepeatStatus.FINISHED;
	}

	/**
	 * @return the interfaceMasterBLO
	 */
	public InterfaceMasterBLO getInterfaceMasterBLO()
	{
		return interfaceMasterBLO;
	}

	/**
	 * @param interfaceMasterBLO_
	 *            the interfaceMasterBLO to set
	 */
	@Required
	public void setInterfaceMasterBLO(final InterfaceMasterBLO interfaceMasterBLO_)
	{
		this.interfaceMasterBLO = interfaceMasterBLO_;
	}

}
