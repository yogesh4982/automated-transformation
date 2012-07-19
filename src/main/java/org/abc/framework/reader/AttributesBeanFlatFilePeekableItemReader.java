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

import org.abc.framework.bean.common.AttributesBean;
import org.abc.framework.bean.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
/**
 *  @author : TCS Date : 04/19/2012 07:00:00 PM
 *  @version : 1.0
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
	 *             Exception
	 * check if previous level 30 record not found then l4Bean is
	 * not created
	 * so create it here so that child list can be set in it
	 * its recordBean will be null and will be checked in processor
	 */
	/*public void getMultiLevelRecordBean(final Object rowDataIn_) 
			throws UnexpectedInputException, ParseException, Exception
	{
		logger.info("Entering in AllocationFileReader");
		Object rowData = rowDataIn_;
		Object nextRowData = null;
		List<MultiLevelRecordBean> level2List = null;
		List<MultiLevelRecordBean> level3List = null;
		List<MultiLevelRecordBean> level4List = null;
		List<MultiLevelRecordBean> level5List = null;
		List<MultiLevelRecordBean> level6List = null;
		List<MultiLevelRecordBean> level7List = null;
		MultiLevelRecordBean level2Bean = null;
		MultiLevelRecordBean level3Bean = null;
		MultiLevelRecordBean level4Bean = null;
		MultiLevelRecordBean level5Bean = null;
		MultiLevelRecordBean level6Bean = null;
		MultiLevelRecordBean level7Bean = null;
		boolean level30RecordFound = false;
		do
		{
			if (rowData instanceof Header)
			{
				getCurrentMultiLevelRecordBean().setRecordBean(rowData);
			}
			else if (rowData instanceof Footer)
			{
				getCurrentMultiLevelRecordBean().setRecordBean(rowData);
			}
			else if (rowData instanceof ASNAlloc05StageBean)
			{
				getCurrentMultiLevelRecordBean().setRecordBean(rowData);
			}
			else if (rowData instanceof ASNAlloc10StageBean)
			{
				if (getCurrentMultiLevelRecordBean().getChildRecordBeanList() == null)
				{
					level2List = new ArrayList<MultiLevelRecordBean>();
					getCurrentMultiLevelRecordBean().setChildRecordBeanList(level2List);
				}
				level2Bean = new MultiLevelRecordBean();
				level2Bean.setRecordBean(rowData);
				level2List.add(level2Bean);
			}
			else if (rowData instanceof ASNAlloc20StageBean)
			{
				if (level2Bean.getChildRecordBeanList() == null)
				{
					level3List = new ArrayList<MultiLevelRecordBean>();
					level2Bean.setChildRecordBeanList(level3List);
				}
				level3Bean = new MultiLevelRecordBean();
				level3Bean.setRecordBean(rowData);
				level3List.add(level3Bean);
			}
			else if (rowData instanceof ASNAlloc30StageBean)
			{
				if (level3Bean.getChildRecordBeanList() == null)
				{
					level4List = new ArrayList<MultiLevelRecordBean>();
					level3Bean.setChildRecordBeanList(level4List);
				}
				level4Bean = new MultiLevelRecordBean();
				level4Bean.setRecordBean(rowData);
				level4List.add(level4Bean);
				level30RecordFound = true;
			}
			else if (rowData instanceof ASNAlloc33StageBean)
			{
				if (!level30RecordFound)
				{
					if (level3Bean.getChildRecordBeanList() == null)
					{
						level4List = new ArrayList<MultiLevelRecordBean>();
						level3Bean.setChildRecordBeanList(level4List);
					}
					level4Bean = new MultiLevelRecordBean();
					level4List.add(level4Bean);
					level30RecordFound = false;
				}
				if (level4Bean.getChildRecordBeanList() == null)
				{
					level5List = new ArrayList<MultiLevelRecordBean>();
					level4Bean.setChildRecordBeanList(level5List);
				}
				level5Bean = new MultiLevelRecordBean();
				level5Bean.setRecordBean(rowData);
				level5List.add(level5Bean);
			}
			else if (rowData instanceof ASNAlloc35StageBean)
			{
				if (level5Bean.getChildRecordBeanList() == null)
				{
					level6List = new ArrayList<MultiLevelRecordBean>();
					level5Bean.setChildRecordBeanList(level6List);
				}
				level6Bean = new MultiLevelRecordBean();
				level6Bean.setRecordBean(rowData);
				level6List.add(level6Bean);
			}
			else if (rowData instanceof ASNAlloc50StageBean)
			{
				if (level6Bean.getChildRecordBeanList() == null)
				{
					level7List = new ArrayList<MultiLevelRecordBean>();
					level6Bean.setChildRecordBeanList(level7List);
				}
				level7Bean = new MultiLevelRecordBean();
				level7Bean.setRecordBean(rowData);
				level7List.add(level7Bean);
			}
			recordCount = recordCount.add(new BigDecimal(1));
			nextRowData = getReader().peek();
			if (nextRowData instanceof ASNAlloc05StageBean || nextRowData instanceof Footer)
			{
				break;
			}
			else
			{
				rowData = getReader().read();
			}
		}
		while (nextRowData != null);
		getStepExecution().getJobExecution().getExecutionContext().put("TOTAL_COUNT_CARTON_FEED", recordCount);
		logger.info("Exiting from AllocationFileReader");
	}*/

	@Override
	public void getAttributesBean(Map<String, String> currentrowData_) throws UnexpectedInputException, ParseException, Exception
	{
		Map<String, String> nextRowData = null;
		
		do
		{
			if ("1".equals(currentrowData_.get(Constants.LEVEL))){
				getCurrentAttributesBean().setAttributes(currentrowData_);
			}
			List<AttributesBean> level2AttributesBeanList = getCurrentAttributesBean().getVarryingFields();
			if ("2".equals(currentrowData_.get(Constants.LEVEL))){
				AttributesBean level2AttributesBean = new AttributesBean();
				level2AttributesBean.setAttributes(currentrowData_);
				level2AttributesBeanList.add(level2AttributesBean);
			}
			
			if (level2AttributesBeanList != null && level2AttributesBeanList.size() > 0){
				List<AttributesBean> level3AttributesBeanList = level2AttributesBeanList.get(level2AttributesBeanList.size() -1).getVarryingFields() ;
				if ("3".equals(currentrowData_.get(Constants.LEVEL))){
					AttributesBean level3AttributesBean = new AttributesBean();
					level3AttributesBean.setAttributes(currentrowData_);
					level3AttributesBeanList.add(level3AttributesBean);
				}
			}
			
			
			
			recordCount = recordCount.add(new BigDecimal(1));
			nextRowData = getReader().peek();
			if (nextRowData != null &&  "1".equals(nextRowData.get(Constants.LEVEL)))
			{
				break;
			}
			else
			{
				currentrowData_ = getReader().read();
			}
		}	while (nextRowData != null);
		
	}

	public int getMaxRecordLevel()
	{
		return maxRecordLevel;
	}

	public void setMaxRecordLevel(int maxRecordLevel)
	{
		this.maxRecordLevel = maxRecordLevel;
	}
}
