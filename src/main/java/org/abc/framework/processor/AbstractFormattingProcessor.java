package org.abc.framework.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.bean.common.StringAndDateConversion;
import org.abc.framework.bean.common.TransformationException;
import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

/******************************************************************************
*
* File Name        : AbstractFormattingProcessor.java
* Description      : Calls the method for formatting data acquired from the file.  
* @author          : TCS
* Date             : 19/04/2012 07:00:00 PM
* @version         : 1.0
* 
*****************************************************************************/
public abstract class AbstractFormattingProcessor implements ItemProcessor<Object, List<String>> , 
StepExecutionListenerAware
{

	private Logger logger = Logger.getLogger(AbstractFormattingProcessor.class);	
	
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;

	private String maxRecordLevel;

	private Integer maxRecordLastLevelRow;

	private List<String> fieldsSkipForSameRow;

	private int totalRecordCount;

	private StepExecution stepExecution;

	private boolean applyLenghFormatting = true;

	private boolean truncateExcessData = true;

	/**
	 * @return the applyLenghFormatting
	 */
	public boolean isApplyLenghFormatting()
	{
		return applyLenghFormatting;
	}

	/**
	 * @param applyLenghFormatting_
	 *            the applyLenghFormatting to set
	 */
	public void setApplyLenghFormatting(final boolean applyLenghFormatting_)
	{
		this.applyLenghFormatting = applyLenghFormatting_;
	}

	/**
	 * @return the truncateExcessData
	 */
	public boolean isTruncateExcessData()
	{
		return truncateExcessData;
	}

	/**
	 * @param truncateExcessData_
	 *            the truncateExcessData to set
	 */
	public void setTruncateExcessData(final boolean truncateExcessData_)
	{
		this.truncateExcessData = truncateExcessData_;
	}

	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount()
	{
		return totalRecordCount;
	}

	/**
	 * @param totalRecordCount_
	 *            the totalRecordCount to set
	 */
	public void setTotalRecordCount(final int totalRecordCount_)
	{
		this.totalRecordCount = totalRecordCount_;
	}

	/**
	 * @return the stepExecution
	 */
	public StepExecution getStepExecution()
	{
		return stepExecution;
	}

	/**
	 * @param stepExecution_
	 *            the stepExecution to set
	 */
	public void setStepExecution(final StepExecution stepExecution_)
	{
		this.stepExecution = stepExecution_;
	}

	/***
	 * This method is called before the Read Write step is starts to execute.
	 * @param stepExecution_
	 *            StepExecution
	 */
	@BeforeStep
	public void beforeStep(final StepExecution stepExecution_)
	{
		this.stepExecution = stepExecution_;
	}

	/**
	 * This method is called after the Read Write step is completes execution.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 * @return ExitStatus ExitStatus
	 */
	@AfterStep
	public ExitStatus afterStep(final StepExecution stepExecution_)
	{
		logger.info("Read Count " + stepExecution_.getReadCount() + "   " + "Write Count "	+ 
					stepExecution_.getWriteCount() + "   " + " Skip Count " + stepExecution_.getSkipCount());
		getExecutionContext()
				.put(Constants.HEADER_FOOTER_TOTAL_COUNT_WITH_HEADER_FOOTER,
						getTotalRecordCount() + 2);
		getExecutionContext()
				.put(Constants.HEADER_FOOTER_TOTAL_COUNT, getTotalRecordCount());
		
		getExecutionContext().put(Constants.HEADER_FOOTER_TOTAL_COUNT_WITH_HEADER,
				getTotalRecordCount() + 1);

		return getExitStatus(stepExecution_);

	}
	/**
	 * This method is called from the afterStep.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 * @return ExitStatus ExitStatus
	 */
	protected ExitStatus getExitStatus(final StepExecution stepExecution_)
	{
		return stepExecution_.getExitStatus();
	}
	
	/**
	 * This method is getter method for Maximum Record Level.
	 * 
	 * @return String
	 */
	public String getMaxRecordLevel()
	{
		return maxRecordLevel;
	}

	/**
	 * This method is setter method for Maximum Record Level.
	 * 
	 * @param maxRecordLevel_
	 *            String
	 */
	public void setMaxRecordLevel(final String maxRecordLevel_)
	{
		this.maxRecordLevel = maxRecordLevel_;
	}

	/**
	 * This method is getter method for Interface configuration bean list.
	 * 
	 * @return List
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	/**
	 * This method is setter method for Interface configuration bean list.
	 * 
	 * @param interfaceConfigurationBeanList_
	 *            the interfaceConfigurationBeanList to set
	 */
	public void setInterfaceConfigurationBeanList(final List<InterfaceConfigurationBean> 
	interfaceConfigurationBeanList_)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList_;
	}

	/**
	 * This method is getter method for MaxRecordLastLevelRow.
	 * 
	 * @return Integer
	 */
	public Integer getMaxRecordLastLevelRow()
	{
		return maxRecordLastLevelRow;
	}

	/**
	 * This method is setter method for MaxRecordLastLevelRow.
	 * 
	 * @param maxRecordLastLevelRow_
	 *            Integer
	 */
	public void setMaxRecordLastLevelRow(final Integer maxRecordLastLevelRow_)
	{
		this.maxRecordLastLevelRow = maxRecordLastLevelRow_;
	}

	/**
	 * This method is getter method for fieldsSkipForSameRow.
	 * 
	 * @return the fieldsSkipForSameRow
	 */
	public List<String> getFieldsSkipForSameRow()
	{
		return fieldsSkipForSameRow;
	}

	/**
	 * This method is setter method for fieldsSkipForSameRow.
	 * 
	 * @param fieldsSkipForSameRow_
	 *            List
	 */
	public void setFieldsSkipForSameRow(final List<String> fieldsSkipForSameRow_)
	{
		this.fieldsSkipForSameRow = fieldsSkipForSameRow_;
	}

	/**
	 * This method will add the required level record to the List.
	 * 
	 * @param dataMap_
	 *            Map
	 * @param dataList_
	 *            List
	 * @param recordLevel_
	 *            String
	 * @param newLineAdded_
	 *            boolean
	 */
	public void addLeveledDataToList(final Map<String, String> dataMap_, final List<String> dataList_,
			final Integer recordLevel_, final boolean newLineAdded_) 
	{
		String stringAttributekeyName;
		String stringAttributeDataForFile;
		boolean addDataToList = true;
		for (int configBeanCount = 0; 
				configBeanCount < interfaceConfigurationBeanList.size(); configBeanCount++)
		{
			stringAttributekeyName = Constants.BLANK_SPACE;
			stringAttributeDataForFile = Constants.BLANK_SPACE;
			if (interfaceConfigurationBeanList.get(configBeanCount).getRecordLevel().equals(recordLevel_.toString()))
			{
				if (!interfaceConfigurationBeanList.get(configBeanCount).getFieldName()
						.equalsIgnoreCase(Constants.FILLER))
				{
					stringAttributekeyName = interfaceConfigurationBeanList.get(configBeanCount).getFieldId();
					
					if (dataMap_.get(stringAttributekeyName) != null)
					{
						stringAttributeDataForFile = dataMap_.get(stringAttributekeyName).toString();
					}
					else
					{
						stringAttributeDataForFile = Constants.BLANK_SPACE;
					}
				}
				else
				{
					if (interfaceConfigurationBeanList.get(configBeanCount).getSourceColumn() != null)
					{
						stringAttributeDataForFile = interfaceConfigurationBeanList.get(configBeanCount)
								.getSourceColumn();
					}
					else
					{
						stringAttributeDataForFile = Constants.BLANK_SPACE;
					}
				}

				if (interfaceConfigurationBeanList.get(configBeanCount).getJavaFormattingString() != null )
				{
					stringAttributeDataForFile = checkAndApplyJavaFormatting(stringAttributeDataForFile,
							interfaceConfigurationBeanList.get(configBeanCount), dataMap_);
				}

				stringAttributeDataForFile = checkAndApplyLengthFormatting(stringAttributeDataForFile,
						interfaceConfigurationBeanList.get(configBeanCount));

				addDataToList = true;
				if (!newLineAdded_ && fieldsSkipForSameRow != null)
				{
					if (fieldsSkipForSameRow.contains(interfaceConfigurationBeanList.get(configBeanCount)
							.getFieldDescription()))
					{
						addDataToList = false;
					}
				}
				// if field length is not set then don't add value to output
				if (interfaceConfigurationBeanList.get(configBeanCount).getOutputFieldLength()==null){
					addDataToList = false;
				}
				if (addDataToList)
				{
					dataList_.add(stringAttributeDataForFile);
				}
			}
		}
	  
	  
	}

	/**
	 * This method will call the required class and method for formatting class
	 * name and method name can be configured colon(:) separated in
	 * AttributeConfigBean.JavaFormattingString.
	 * 
	 * @param stringAttributeDataForFileIn_
	 *            String
	 * @param interfaceConfigurationBean_
	 *            InterfaceConfigurationBean
	 * @param dataMap_
	 *            Map           
	 * @return String
	 */
	public String checkAndApplyJavaFormatting(final String stringAttributeDataForFileIn_,
			final InterfaceConfigurationBean interfaceConfigurationBean_, final Map<String, String> dataMap_)
	{
		String stringAttributeDataForFile = stringAttributeDataForFileIn_;
		if (interfaceConfigurationBean_.getJavaFormattingString() != null && 
				!interfaceConfigurationBean_.getJavaFormattingString().equals(Constants.BLANK_SPACE) && 
				interfaceConfigurationBean_.getJavaFormattingString().contains(":"))
		{

			final String stringJavaFormattingString = interfaceConfigurationBean_.getJavaFormattingString();
			final String[] arraySplitedString = stringJavaFormattingString.split(":");
			final String stringClassName = arraySplitedString[0];
			final String stringMethodName = arraySplitedString[1];
			String passDataMap = Constants.BLANK_SPACE;
			if (arraySplitedString.length == 3)
			{
				passDataMap = arraySplitedString[2];
			}

			Object[] paramsObjectsForMethod = null;
			if (Constants.BLANK_SPACE.equals(passDataMap))
			{
				final Object[] paramsObjectsForMethodTemporary = { stringAttributeDataForFile,
						interfaceConfigurationBean_, };
				paramsObjectsForMethod = paramsObjectsForMethodTemporary;
			}
			else
			{
				final Object[] paramsObjectsForMethodTemporary = { stringAttributeDataForFile,
						interfaceConfigurationBean_, dataMap_, };
				paramsObjectsForMethod = paramsObjectsForMethodTemporary;
			}
			stringAttributeDataForFile = StringAndDateConversion.invokeJavaMethodToFormatString(stringClassName,
					stringMethodName, paramsObjectsForMethod);

		}

		return stringAttributeDataForFile;
	}

	/**
	 * This method will apply the required length formatting.
	 * 
	 * @param stringAttributeDataForFileIn_
	 *            String
	 * @param interfaceConfigurationBean_
	 *            InterfaceConfigurationBean
	 * @return String
	 */
	public String checkAndApplyLengthFormatting(final String stringAttributeDataForFileIn_,
			final InterfaceConfigurationBean interfaceConfigurationBean_)
	{
		String stringAttributeDataForFile = stringAttributeDataForFileIn_;
		Formatter formatter = null;
		if (interfaceConfigurationBean_.getOutputFieldLength() != null &&
			!interfaceConfigurationBean_.getOutputFieldLength().equals(Constants.BLANK_SPACE))
		{
			// for truncate string more then field length
			if (truncateExcessData)
			{
				if(stringAttributeDataForFile.length() > 
					Integer.parseInt(interfaceConfigurationBean_.getOutputFieldLength()))
				{
					stringAttributeDataForFile = stringAttributeDataForFile.substring(0,
							Integer.parseInt(interfaceConfigurationBean_.getOutputFieldLength()));
				}
			}

			// for applying length formatting
			if (applyLenghFormatting)
			{
				formatter = new Formatter();
				String formatString = Constants.BLANK_SPACE;
				if (interfaceConfigurationBean_.getAlignmentIndicator().equalsIgnoreCase("L"))
				{
					formatString = "%-" +
							interfaceConfigurationBean_.getOutputFieldLength() + "s";
				}
				else if (interfaceConfigurationBean_.getAlignmentIndicator().equalsIgnoreCase("R"))
				{
					formatString = "%" + 
							interfaceConfigurationBean_.getOutputFieldLength() +	"s";
				}
				else
				{
					formatString = "%" + 
							interfaceConfigurationBean_.getOutputFieldLength() +	"s";
				}
				stringAttributeDataForFile = formatter.format(formatString, stringAttributeDataForFile).toString();
			}
		}
		return stringAttributeDataForFile;
	}

	/**
	 * Adds new line after checking for levels.
	 * 
	 * @param fieldCount_
	 *            int
	 * @param dataList_
	 *            List
	 * @param recordLevel_
	 *            Integer
	 * @return boolean
	 */
	public boolean addNewLine(final int fieldCount_, final List<String> dataList_, final Integer recordLevel_)
	{
		boolean newLineAdded = false;
		boolean isNewLineRequired = false;
		if (maxRecordLevel != null)
		{
			if (recordLevel_ < Integer.parseInt(maxRecordLevel))
			{
				// for the level change before last level new line is required
				isNewLineRequired = true;
			}
			else if(Integer.parseInt(maxRecordLevel) == recordLevel_)
			{
				// for the first field of the last level new line is required
				if (fieldCount_ == 0)
				{
					isNewLineRequired = true;
				}
				else if (maxRecordLastLevelRow != null)
				{
					// maxRecordLastLevelRow is not null so check for that count has reach
					if (fieldCount_  % maxRecordLastLevelRow == 0)
					{
						isNewLineRequired = true;
					}
				}
				else
				{
					// maxRecordLastLevelRow is null so each time new line is required
					isNewLineRequired = true;
				}
			}
		}
		else
		{
			// for the level change new line is required
			isNewLineRequired = true;
		}
		
		if(isNewLineRequired)
		{
			dataList_.add(Constants.NEW_LINE);
			newLineAdded = true;
			totalRecordCount++;
		}
		return newLineAdded;
	}

	/**
	 * This Method will convert any bean object to Map with key as its field
	 * name in upper case and value as its value.
	 * 
	 * @param object_
	 *            Object
	 * @param recordLevel_
	 *            Integer
	 * @return List Returns the List of data to be write to the file.
	 */
	public Map<String, String> beanToMap(final Object object_,final Integer recordLevel_)
	{

		if (object_ == null)
		{
			return null;
		}
		final Map<String, String> beanDataMap = new HashMap<String, String>();
		final Class beanClassName = object_.getClass();
		final Field[] beanFieldsList = beanClassName.getDeclaredFields();
		final Class[] paramsClassesForMethod = {};
		final Object[] paramsObjectsForMethod = {};
		String key = Constants.BLANK_SPACE;
		String value = Constants.BLANK_SPACE;
		String fieldId = Constants.BLANK_SPACE;
		
		final Map<String,String> sourceColumnToFieldIdMap = (Map<String,String>)(getExecutionContext().
				get("sourceColumnToFieldIdMap"));

		for (Field beanField : beanFieldsList)
		{
			try
			{
				key = beanField.getName();
				final String methodName = "get" +
						key.substring(0, 1).toUpperCase() +
						key.substring(1, key.length());
				final Method fieldGetterMethod = beanClassName.getDeclaredMethod(methodName, paramsClassesForMethod);
				value = (String) fieldGetterMethod.invoke(object_, paramsObjectsForMethod);
				
				if(sourceColumnToFieldIdMap != null && 
				   sourceColumnToFieldIdMap.containsKey(key.toUpperCase()+Constants.UNDER_SCORE+recordLevel_))
				{
					fieldId = sourceColumnToFieldIdMap.get(key.toUpperCase()+Constants.UNDER_SCORE+recordLevel_);
					beanDataMap.put(fieldId, value);
				}
			}
			catch (IllegalArgumentException e)
			{
				throw new TransformationException(e.getMessage());
			}
			catch (IllegalAccessException e)
			{
				throw new TransformationException(e.getMessage());
			}
			catch (InvocationTargetException e)
			{
				throw new TransformationException(e.getMessage());
			}
			catch (NoSuchMethodException e)
			{
				throw new TransformationException(e.getMessage());
			}
		}

		return beanDataMap;
	}

	/**
	 * This method calls the method addLeveledDataToList as per the levels
	 * available in attributesBean.
	 * 
	 * @param object_
	 *            Object
	 * @return List
	 * 
	 */
	public abstract List<String> process(final Object object_);
	
	/**
	 * This Method will return field id based on the source column
	 * and record level.
	 * 
	 * @param sourceColumn_
	 *            String
	 * @param recordLevel_
	 *            int
	 * @return String field id of the source column
	 */
	
	public String getFieldIdFromSourceColumnAndRecordLevel(final String sourceColumn_,final int recordLevel_)
	{
		String fieldId = Constants.BLANK_SPACE;
		final Map<String, String> sourceColumnToFieldIdMap = (Map<String, String>)(getExecutionContext().
				get("sourceColumnToFieldIdMap"));
		if (sourceColumnToFieldIdMap != null && 
				sourceColumnToFieldIdMap.containsKey(sourceColumn_.toUpperCase() + 
						Constants.UNDER_SCORE + recordLevel_))
		{
			fieldId = sourceColumnToFieldIdMap.get(sourceColumn_.toUpperCase() + 
					Constants.UNDER_SCORE + recordLevel_);
		}
		
		return fieldId;
	}

	/**
	 * This Method will return execution context for value to be stored.
	 * 
	 * @return ExecutionContext
	 */
	protected ExecutionContext getExecutionContext()
	{
		return getStepExecution().getJobExecution()
				.getExecutionContext();
	}
}
