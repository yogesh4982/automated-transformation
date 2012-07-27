package org.abc.framework.reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.bean.common.TransformationSystemException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.beans.factory.annotation.Required;
/******************************************************************************
*
* File Name        : AggregateJdbcCursorPeekableAttributesBeanItemReader.java
* Description      : Calls the method for getting attributes.  
* @author          : TCS
* Date             : 4/3/2012 07:41:00 PM
* @version         : 1.0
* 
*****************************************************************************/
public class AggregateJdbcCursorPeekableAttributesBeanItemReader implements
		ItemReader<AttributesBean>, ItemStream {

	private SingleItemPeekableItemReader<HashMap<String, String>> reader;

	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;

	private AttributesBean currentRecordAttributesBean;

	private String maxRecordLevel;
	/**
	 * This is the getter method for List of InterfaceConfigurationBean.
	 * @return List
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList() {
		return interfaceConfigurationBeanList;
	}
	/**
	 * This is the setter method for List of InterfaceConfigurationBean.
	 * @param interfaceConfigurationBeanList__
	 * 			  List
	 */
	public void setInterfaceConfigurationBeanList(
			final List<InterfaceConfigurationBean> interfaceConfigurationBeanList__) {
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList__;
	}
	/**
	 * This is the getter method for MaxRecordLevel.
	 * 
	 * @return String
	 */
	public String getMaxRecordLevel() {
		return maxRecordLevel;
	}

	/**
	 * This is the setter method for MaxRecordLevel.
	 * 
	 * @param maxRecordLevel_
	 *            String
	 */
	@Required
	public void setMaxRecordLevel(final String maxRecordLevel_) {
		this.maxRecordLevel = maxRecordLevel_;
	}

	/**
	 * This method will read the result set with group by KEY fields.
	 * @return AttributesBean
	 */
	public AttributesBean read() 
	{
		try
		{
			final Map<String, String> rowData = reader.read();
			currentRecordAttributesBean = new AttributesBean();
			if (rowData != null) {
				getAttributeBean(rowData);
			} else {
				return null;
			}
		}
		catch (Exception exception) {
			throw new TransformationSystemException(exception.getMessage());
		}
		return currentRecordAttributesBean;
	}

	/**
	 * Method keep on peeking next records until it hits next group.
	 * 
	 * @param rowDataNew_
	 *            -Current record
	 */
	private void getAttributeBean(final Map<String, String> rowDataNew_)
	{
		try
		{
			Map<String, String> rowData = rowDataNew_;
			Map<String, String> nextRowData = null;
			Map<String, String> previousRowData = null;
			boolean isLevel1Changed = true;
	
			do {
				// Map level 1 bean to currentRecordAttributesBean
				mapLevelRecordsToAttributesBean(rowData, currentRecordAttributesBean, 1);
				// map all levels below level 2 to currentRecordAttributesBean
				mapLevel(currentRecordAttributesBean, 2,rowData, previousRowData,isLevel1Changed);
				// Get the next record
				nextRowData = reader.peek();
				// Check if next record is changed
				isLevel1Changed = levelChanged(rowData, nextRowData, 1);
				
				// break the loop when end of file encountered
				// or level 1 is changed.
				if (nextRowData != null && !isLevel1Changed) {
					previousRowData = rowData;
					rowData = reader.read();
					nextRowData = reader.peek();
				} else {
					break;
				}
			} while (rowData != null);
		}
		catch (Exception exception) {
			throw new TransformationSystemException(exception.getMessage());
		}
	}

	/**
	 * Maps the current row to the Attributes bean.
	 * 
	 * @param currentRowData_
	 * 			  Map
	 * @param attributesBean_
	 * 			  AttributesBean
	 * @param level_
	 * 			  int
	 */
	private void mapLevelRecordsToAttributesBean(final Map<String, String> currentRowData_,
			final AttributesBean attributesBean_, final int level_)
	{
		for (InterfaceConfigurationBean interfaceConfigurationBean : interfaceConfigurationBeanList)
		{
			if (!interfaceConfigurationBean.getFieldName().equalsIgnoreCase(Constants.FILLER))
			{
				if (Integer.parseInt(interfaceConfigurationBean.getRecordLevel()) == level_)
				{
					attributesBean_.setAttribute(interfaceConfigurationBean.getFieldId(),
					currentRowData_.get(interfaceConfigurationBean.getFieldId()));
					
				}
			}
		}
	}

	/**
	 * Checks if level is changed.
	 * 
	 * @param currentRowData_
	 * 			  Map
	 * @param otherRowData_
	 * 			  Map
	 * @param level_
	 * 			  int
	 * @return boolean
	 */
	private boolean levelChanged(final Map<String, String> currentRowData_, final Map<String, String> otherRowData_,
			final int level_)
	{
		boolean flag;
		if (otherRowData_ == null)
		{
			return true;
		}
		for (InterfaceConfigurationBean interfaceConfigurationBean : interfaceConfigurationBeanList)
		{
			if (!interfaceConfigurationBean.getFieldName().equalsIgnoreCase(Constants.FILLER))
			{
				if (Integer.parseInt(interfaceConfigurationBean.getRecordLevel()) == level_ && currentRowData_
						.get(interfaceConfigurationBean.getFieldId()) != null)
				{
					flag = currentRowData_
							.get(interfaceConfigurationBean.getFieldId()).equals(
									otherRowData_.get(interfaceConfigurationBean.getFieldId()));
					if (!flag)
					{
						return !flag;
					}
				}
			}
		}

		return false;
	}

	/**
	 * This method is used to open Execution Context.
	 * 
	 * @param executionContext_
	 *            ExecutionContext
	 * @throws ItemStreamException
	 *             Exception
	 */
	public void open(final ExecutionContext executionContext_)
			throws ItemStreamException {
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
	public void update(final ExecutionContext executionContext_)
			throws ItemStreamException {
		reader.update(executionContext_);

	}

	/**
	 * This method is used to close Execution Context.
	 * 
	 * @throws ItemStreamException
	 *             Exception
	 */
	public void close() throws ItemStreamException {
		reader.close();

	}
	/**
	 * This method is getter method for Reader.
	 * 
	 * @return SingleItemPeekableItemReader
	 */
	public SingleItemPeekableItemReader<HashMap<String, String>> getReader() {
		return reader;
	}
	/**
	 * This is the setter method for Reader.
	 * 
	 * @param reader_
	 *            SingleItemPeekableItemReader
	 */
	@Required
	public void setReader(
			final SingleItemPeekableItemReader<HashMap<String, String>> reader_) {
		this.reader = reader_;
	}

	/**
	 * This method maps the current level and 
	 * calls its self to map next levels.
	 * @param currentLevelBean_
	 *            AttributesBean
	 * @param nextLevel_
	 *            int
	 * @param rowData_
	 *            Map
	 * @param previousRowData_
	 *            Map
	 * @param ispreviousLevelChanged_
	 *            boolean
	 */
	private void mapLevel(final AttributesBean currentLevelBean_, final int nextLevel_,
			final Map<String, String> rowData_, final Map<String, String> previousRowData_,
			final boolean ispreviousLevelChanged_)
	{
		boolean isNextLevelChangedFlag = false;
		AttributesBean nextLevelBean = null;
		if (Integer.parseInt(maxRecordLevel) >= nextLevel_)
		{
			final List<AttributesBean> nextLevelList = currentLevelBean_.getVarryingFields();
			//  check if current row data changed from the previous data for the 
			// current level
			isNextLevelChangedFlag = levelChanged(rowData_, previousRowData_, nextLevel_);
			if (isNextLevelChangedFlag || ispreviousLevelChanged_)
			{
				isNextLevelChangedFlag = true;
				nextLevelBean = new AttributesBean();
				mapLevelRecordsToAttributesBean(rowData_, nextLevelBean, nextLevel_);
				nextLevelList.add(nextLevelBean);
			}
			else
			{
				//if current level is not changed then
				// get the last bean in the varrying field
				nextLevelBean = nextLevelList.get(nextLevelList.size() - 1);
				isNextLevelChangedFlag = false;
			}
			mapLevel(nextLevelBean, nextLevel_ + 1, rowData_, previousRowData_,
					isNextLevelChangedFlag);
		}

	}

}
