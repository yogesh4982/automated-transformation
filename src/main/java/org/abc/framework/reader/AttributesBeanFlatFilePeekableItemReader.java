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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author : TCS Date : 04/19/2012 07:00:00 PM
 * @version : 1.0
 */

public class AttributesBeanFlatFilePeekableItemReader extends AbstractFlatFilePeekableItemReader
{

	private Logger logger = Logger.getLogger(AttributesBeanFlatFilePeekableItemReader.class);

	private BigDecimal recordCount = new BigDecimal(0);

	private int maxRecordLevel;

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
	public void getAttributesBean(Map<String, String> currentrowData_) throws UnexpectedInputException, ParseException,
			Exception
	{
		Map<String, String> nextRowData = null;

		do
		{
			if ("1".equals(currentrowData_.get(Constants.LEVEL)))
			{
				getCurrentAttributesBean().setAttributes(currentrowData_);
			}
			setVarryingFields(currentrowData_, getCurrentAttributesBean(), 1);

			recordCount = recordCount.add(new BigDecimal(1));
			nextRowData = getReader().peek();
			if (nextRowData != null && "1".equals(nextRowData.get(Constants.LEVEL)))
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

	public int getMaxRecordLevel()
	{
		return maxRecordLevel;
	}

	public void setMaxRecordLevel(int maxRecordLevel)
	{
		this.maxRecordLevel = maxRecordLevel;
	}

	private void setVarryingFields(Map<String, String> currentrowData_, AttributesBean currentLevelAttributesBean,
			int currentLevel)
	{
		List<AttributesBean> nextLevelAttributesBeanList = currentLevelAttributesBean.getVarryingFields();
		int nextLevel = currentLevel + 1;
		if (nextLevel > maxRecordLevel)
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
