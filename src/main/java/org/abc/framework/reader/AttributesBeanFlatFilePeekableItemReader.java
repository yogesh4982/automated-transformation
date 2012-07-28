/******************************************************************************
 * 
 * File Name : AllocationFileReader.java : Class for flat file read implementing 
 * abstract method getMultiLevelRecordBean
 *  
 * @author : TCS Date : 04/19/2012 07:00:00 PM
 * @version : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.reader;

import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.common.Constants;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author : 
 * Date : 04/19/2012 07:00:00 PM
 * @version : 1.0
 */

public class AttributesBeanFlatFilePeekableItemReader extends AbstractPeekableItemReader 
{

	
	/**
	 * This method is use to get AttributeBean.
	 * 
	 * @param rowDataIn_
	 *            Object
	 * @throws UnexpectedInputException
	 *             Exception
	 * @throws ParseException
	 *             Exception
	 * @throws Exception
	 *             Exception check if previous level 30 record not found then
	 *             l4Bean is not created so create it here so that child list
	 *             can be set in it its recordBean will be null and will be
	 *             checked in processor
	 */

	@Override
	protected void getAttributeBean(Map<String, String> currentrowData_) throws UnexpectedInputException, ParseException,
			Exception
	{
		Map<String, String> nextRowData = null;

		do
		{
			super.getAttributeBean(currentrowData_);
			// for header level = 0
			// for trailer level = -1
			if (Constants.HEADER_RECORD_LEVEL.equals(currentrowData_.get(Constants.LEVEL)) ||
					Constants.TRAILER_RECORD_LEVEL.equals(currentrowData_.get(Constants.LEVEL)))
			{
				getCurrentAttributesBean().setAttributes(currentrowData_);
				break;
			} else if ("1".equals(currentrowData_.get(Constants.LEVEL)))
			{
				getCurrentAttributesBean().setAttributes(currentrowData_);
			}
			setVarryingFields(currentrowData_, getCurrentAttributesBean(), 1);

			nextRowData = getReader().peek();
			if (nextRowData != null && ("1".equals(nextRowData.get(Constants.LEVEL)) ||
					Constants.TRAILER_RECORD_LEVEL.equals(nextRowData.get(Constants.LEVEL))))
			{
				break;
			}
			else
			{
				currentrowData_ = getReader().read();
			}
		}
		while (nextRowData != null);

	}

	

	private void setVarryingFields(Map<String, String> currentrowData_, AttributesBean currentLevelAttributesBean,
			int currentLevel)
	{
		List<AttributesBean> nextLevelAttributesBeanList = currentLevelAttributesBean.getVarryingFields();
		int nextLevel = currentLevel + 1;
		if (nextLevel > Integer.parseInt(maxRecordLevel))
		{
			return;
		}
		if ((String.valueOf(nextLevel)).equals(currentrowData_.get(Constants.LEVEL)))
		{
			AttributesBean nextLevelAttributesBean = new AttributesBean();
			nextLevelAttributesBean.setAttributes(currentrowData_);
			nextLevelAttributesBeanList.add(nextLevelAttributesBean);
			return;
		}
		else if (nextLevel <= Integer.parseInt(currentrowData_.get(Constants.LEVEL)))
		{
			setVarryingFields(currentrowData_, nextLevelAttributesBeanList.get(nextLevelAttributesBeanList.size() - 1),
					nextLevel);
		}

	}
	
	
	
	
}
