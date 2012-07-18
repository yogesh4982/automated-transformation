/******************************************************************************
 *
 * File Name        : InterfaceConfigurationQueryProvider.java
 * Description      :  
 * Author           : 
 * Date             : 29/3/2012 07:17:00 PM
 * Version          : 1.0
 * 
 *****************************************************************************/
package org.abc.framework.bean.common;

import java.util.ArrayList;
import java.util.List;

import org.abc.framework.bean.InterfaceConfigurationBean;

/**
 * @author TCS
 * @version 1.0
 *
 */
public class InterfaceConfigurationQueryProvider
{
	/**
	 * This method is used to make query.
	 * 
	 * @param orderedAttributeList_
	 *            List
	 * @param additionalTable_ String 
	 * @return String 
	 */
	public String getDynamicSelectQueryForInterface(final List<InterfaceConfigurationBean> orderedAttributeList_, 
			final String additionalTable_)
	{
		final StringBuffer selectString = new StringBuffer(1024);

		selectString.append(" SELECT ");

		final List<String> tableNameList = new ArrayList<String>();
		if(additionalTable_!= null && !Constants.BLANK_SPACE.equals(additionalTable_))
		{
			tableNameList.add(additionalTable_);
		}

		for (int index = 0; index < orderedAttributeList_.size(); index++)
		{
			if (!orderedAttributeList_.get(index).getFieldName().equalsIgnoreCase(Constants.FILLER))
			{
				
				if(!tableNameList.contains(orderedAttributeList_.get(index).getSource()))
				{
					tableNameList.add(orderedAttributeList_.get(index).getSource());
				}
				
				if (orderedAttributeList_.get(index).getDataFormattingString() == null || 
						orderedAttributeList_.get(index).getDataFormattingString().equals(Constants.BLANK_SPACE))
				{
					selectString.append(orderedAttributeList_.get(index).getSourceColumn().trim());
				}
				else
				{
					selectString.append(replaceWithDBFormattingString(orderedAttributeList_.get(index)
							.getDataFormattingString(), "$", orderedAttributeList_.get(index).getSourceColumn().trim()));
				}
				selectString.append(" AS \"" + orderedAttributeList_.get(index).getFieldId() + "\" ");
				if (index < orderedAttributeList_.size() - 1)
				{
					selectString.append(",");
				}
			}
		}
		// in case of filler as a last attribute
		if (selectString.toString().endsWith(","))
		{
			selectString.append(" 1 ");
		}
		selectString.append(" FROM ");
		for(int tableNameCount = 0;tableNameCount < tableNameList.size();tableNameCount++ )
		{
			selectString.append(tableNameList.get(tableNameCount));
			if(tableNameCount < tableNameList.size()-1)
			{
				selectString.append(", ") ;
			}	
			
		}
		
		return selectString.toString();

	}

	/**
	 * This function will Replace pattern to Column name in DBFormating String.
	 * 
	 * @param dbFormattingString_
	 *            String
	 * @param pattern_
	 *            String
	 * @param columnName_
	 *            String
	 * @return String
	 */
	private String replaceWithDBFormattingString(final String dbFormattingString_,
			final String pattern_,final String columnName_)
	{
		int start = 0;
		int end = 0;
		final StringBuffer result = new StringBuffer(1024);

		while ((end = dbFormattingString_.indexOf(pattern_, start)) >= 0)
		{
			result.append(dbFormattingString_.substring(start, end));
			result.append(columnName_);
			start = end + pattern_.length();
		}
		result.append(dbFormattingString_.substring(start));
		return result.toString();
	}

	/**
	 * This function will give format string for interface.
	 * 
	 * @param orderedAttributeList_
	 *            List
	 * @return String
	 */
	static String getFormatStringForInterface(final List<InterfaceConfigurationBean> orderedAttributeList_)
	{

		final StringBuffer formatString = new StringBuffer();
		for (int i = 0; i < orderedAttributeList_.size(); i++)
		{
			if ("L".equalsIgnoreCase(orderedAttributeList_.get(i).getAlignmentIndicator()))
			{
				formatString.append("%-" + 
						orderedAttributeList_.get(i).getOutputFieldLength() + 
						"s");
			}
			else if ("R".equalsIgnoreCase(orderedAttributeList_.get(i).getAlignmentIndicator()))
			{
				formatString.append("%" + 
						orderedAttributeList_.get(i).getOutputFieldLength() + 
						"s");
			}
			else
			{
				formatString.append("%" + 
						orderedAttributeList_.get(i).getOutputFieldLength() + 
						"s");
			}
		}

		return formatString.toString();

	}

}
