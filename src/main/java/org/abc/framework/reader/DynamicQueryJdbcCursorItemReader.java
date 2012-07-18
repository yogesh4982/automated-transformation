/******************************************************************************
 *
 * File Name        : DynamicQueryJdbcCursorItemReader.java
 * Description      :  
 * Author           : 
 * Date             : 29/3/2012 07:33:00 PM
 * Version          : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.reader;

import java.util.List;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.bean.common.InterfaceConfigurationQueryProvider;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.JdbcCursorItemReader;

/**
 * @author TCS
 * @version 1.0
 */
public class DynamicQueryJdbcCursorItemReader extends JdbcCursorItemReader
{

	private Logger logger = Logger.getLogger(DynamicQueryJdbcCursorItemReader.class);
	
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;
	
	private String orderByClause;
	
	private String whereClause;

	private String additionalTable;
	
	private String groupByClause;
	
	
	/**
	 * This method is setter method for interface configuration bean list.
	 * 
	 * @param interfaceConfigurationBeanList_
	 *            List
	 */
	public void setInterfaceConfigurationBeanList(final List<InterfaceConfigurationBean> 
		interfaceConfigurationBeanList_)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList_;
		final InterfaceConfigurationQueryProvider interfaceConfigurationQueryProvider = 
				new InterfaceConfigurationQueryProvider();
		String queryString = interfaceConfigurationQueryProvider.getDynamicSelectQueryForInterface(
				interfaceConfigurationBeanList_, additionalTable);
		if(whereClause!=null)
		{
			queryString = queryString + Constants.NEW_LINE + getWhereClause();
		}
		if(groupByClause!=null)
		{
			queryString = queryString + Constants.NEW_LINE + getGroupByClause();
		}
		if(orderByClause!=null)
		{
			queryString = queryString + Constants.NEW_LINE + getOrderByClause();
		}
		
		logger.info("Query executed > "+queryString);
		setSql(queryString);
	}
	
	/**
	 * @return the groupByClause
	 */
	public String getGroupByClause()
	{
		return groupByClause;
	}

	/**
	 * @param groupByClause_ the groupByClause to set
	 */
	public void setGroupByClause(final String groupByClause_)
	{
		this.groupByClause = groupByClause_;
	}

	/**
	 * @return the additionalTable
	 */
	
	public String getAdditionalTable()
	{
		return additionalTable;
	}

	/**
	 * @param additionalTable_ the additionalTable to set
	 */
	
	public void setAdditionalTable(final String additionalTable_)
	{
		this.additionalTable = additionalTable_;
	}

	/**
	 * @return the whereClause
	 */
	public String getWhereClause()
	{
		return whereClause;
	}

	/**
	 * @param whereClause_ the whereClause to set
	 */
	public void setWhereClause(final String whereClause_)
	{
		this.whereClause = whereClause_;
	}

	/**
	 * @return the orderByClause
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * @param orderByClause_ the orderByClause to set
	 */
	public void setOrderByClause(final String orderByClause_) {
		this.orderByClause = orderByClause_;
	}

	/**
	 * This method is getter method for interface configuration bean list.
	 * 
	 * @return List
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

}
