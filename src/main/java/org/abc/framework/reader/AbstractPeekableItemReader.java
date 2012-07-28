/******************************************************************************
 * 
 * File Name : AbstractFlatFilePeekableItemReader.java : Abstract Class for flat file read
 *  
 * @author : TCS Date : 04/3/2012 07:00:00 PM
 * @version : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.reader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author : TCS Date : 04/3/2012 07:00:00 PM
 * @version : 1.0
 * */
public abstract class AbstractPeekableItemReader implements ItemReader<AttributesBean>, ItemStream ,InitializingBean
{

	protected SingleItemPeekableItemReader<HashMap<String, String>> reader;

	protected List<InterfaceConfigurationBean> interfaceConfigurationBeanList;
	
	protected AttributesBean currentRecordAttributesBean;
	
	protected String maxRecordLevel;

	private StepExecution stepExecution;
	
	private BigDecimal recordCount= new BigDecimal(0);;
	
	private BigDecimal hashCount = new BigDecimal(0);;
	
	private List<String> hashCountFieldIds;
	/**
	 * @return the currentAttributesBean
	 */
	public AttributesBean getCurrentAttributesBean()
	{
		return currentRecordAttributesBean;
	}


	/**
	 * @param currentAttributesBean_ the currentAttributesBean to set
	 */
	public void setCurrentAttributesBean(final AttributesBean currentAttributesBean_)
	{
		this.currentRecordAttributesBean = currentAttributesBean_;
	}


	/**
	 * @return the stepExecution
	 */
	public StepExecution getStepExecution()
	{
		return stepExecution;
	}


	/**
	 * @param stepExecution_ the stepExecution to set
	 */
	public void setStepExecution(final StepExecution stepExecution_)
	{
		this.stepExecution = stepExecution_;
	}


	/***
	 * 
	 * @param stepExecution_ StepExecution
	 */
	@BeforeStep
	public void beforeStep(final StepExecution stepExecution_)
	{
		this.stepExecution = stepExecution_;
	} 

	
	/**
	 * This is the getter method for InterfaceConfigurationBean List.
	 * 
	 * @return List
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	/**
	 * This is the setter method for InterfaceConfigurationBean List.
	 * 
	 * @param interfaceConfigurationBeanList_
	 *            List
	 */
	public void 
	setInterfaceConfigurationBeanList(final List<InterfaceConfigurationBean> interfaceConfigurationBeanList_)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList_;
	}

	/**
	 * This method is used to open Execution Context.
	 * 
	 * @param executionContext_
	 *            ExecutionContext
	 * @throws ItemStreamException
	 *             Exception
	 */
	public void open(final ExecutionContext executionContext_) throws ItemStreamException
	{
		reader.open(executionContext_);

	}

	/**
	 * This method is used to update Execution Context.
	 * 
	 * @param executionContext_
	 *            ExecutionContext
	 * @throws ItemStreamException
	 *             Exception
	 */
	public void update(final ExecutionContext executionContext_) throws ItemStreamException
	{
		reader.update(executionContext_);

	}

	/**
	 * This method is used to close Execution Context.
	 * 
	 * @throws ItemStreamException
	 *             Exception
	 */
	public void close() throws ItemStreamException
	{
		reader.close();

	}

	/**
	 * This method is getter method for Reader.
	 * 
	 * @return SingleItemPeekableItemReader
	 */
	public SingleItemPeekableItemReader<HashMap<String, String>> getReader()
	{
		return reader;
	}

	/**
	 * This is the setter method for Reader.
	 * 
	 * @param reader_
	 *            SingleItemPeekableItemReader
	 */
	@Required
	public void setReader(final SingleItemPeekableItemReader<HashMap<String, String>> reader_)
	{
		this.reader = reader_;
	}

	/**
	 * This method is used to read Reader.
	 * 
	 * @return AttributesBean
	 * 
	 * @throws Exception
	 *             Exception
	 * @throws UnexpectedInputException
	 *             Exception
	 * @throws ParseException
	 *             Exception
	 * @throws NonTransientResourceException
	 *             Exception
	 */
	public AttributesBean read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException
	{

		final Map<String,String> rowData = reader.read();
		currentRecordAttributesBean = new AttributesBean();
		if (rowData != null)
		{
			
			getAttributeBean(rowData);
		}
		else
		{
			return null;
		}
				
		return currentRecordAttributesBean;

	}

	/***
	 * 
	 * @param rowData_ Object
	 * @throws UnexpectedInputException UnexpectedInputException
	 * @throws ParseException ParseException
	 * @throws Exception Exception
	 */
	protected  void getAttributeBean(Map<String, String> rowData_) 
			throws UnexpectedInputException, ParseException, Exception
			{
					updateRecordCount();
					updateHashCount(rowData_);
			}

	/**
	 * This method updates the record count in execution context.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 * @return ExitStatus ExitStatus
	 */
	protected void updateRecordCount()
	{
		recordCount = recordCount.add(new BigDecimal(1));
		stepExecution.getExecutionContext().put(Constants.RECORD_COUNT, recordCount);
	}
	
	/**
	 * This method calculates and updates the hash count in execution context.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 * @return ExitStatus ExitStatus
	 */
	
	protected void updateHashCount(Map<String,String> rowData) {
	//	InterfaceConfigurationBean = getFieldIdToConfigBean();
		Set<String> fieldIds = rowData.keySet();
		for(String fieldId:fieldIds)
		{
			if (hashCountFieldIds.contains(fieldId)){
				String valueForHashtCount = rowData.get(fieldId);
				hashCount = hashCount.add(new BigDecimal(valueForHashtCount));
			}
		}
		stepExecution.getExecutionContext().put(Constants.HASH_COUNT, hashCount);
	}
	
	public void populateHashCountFieldIds()
	{
		hashCountFieldIds = new ArrayList<String>();
		for(InterfaceConfigurationBean interfaceConfigurationBean: getInterfaceConfigurationBeanList()){
			if ("Y".equals(interfaceConfigurationBean.getIncludeInHashtotal())){
				hashCountFieldIds.add(interfaceConfigurationBean.getFieldId());
			}
		}
		
			
	}

	public void afterPropertiesSet() throws Exception {
		populateHashCountFieldIds();
		
	}


	/**
	 * @return the maxRecordLevel
	 */
	public String getMaxRecordLevel() {
		return maxRecordLevel;
	}


	/**
	 * @param maxRecordLevel the maxRecordLevel to set
	 */
	public void setMaxRecordLevel(String maxRecordLevel) {
		this.maxRecordLevel = maxRecordLevel;
	}
	
	
}
