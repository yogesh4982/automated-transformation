package org.abc.framework.reader;

import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.bean.common.TransformationSystemException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
/******************************************************************************
*
* File Name        : AggregateJdbcCursorPeekableAttributesBeanItemReader.java
* Description      : Calls the method for getting attributes.  
* @author          : TCS
* Date             : 4/3/2012 07:41:00 PM
* @version         : 1.0
* 
*****************************************************************************/
public class AttributeBeanJdbcCursorPeekableItemReader extends AbstractPeekableItemReader {

	
	


	/**
	 * Method keep on peeking next records until it hits next group.
	 * 
	 * @param rowDataNew_
	 *            -Current record
	 */
	protected void getAttributeBean(final Map<String, String> rowDataNew_)
	{
		try
		{
			Map<String, String> rowData = rowDataNew_;
			Map<String, String> nextRowData = null;
			Map<String, String> previousRowData = null;
			boolean isLevel1Changed = true;
	
			do {
				if (!(Constants.HEADER_RECORD_LEVEL.equals(rowDataNew_
						.get(Constants.LEVEL)) || Constants.TRAILER_RECORD_LEVEL
						.equals(rowDataNew_.get(Constants.LEVEL)))) {
					super.getAttributeBean(rowDataNew_);
				}
				
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
