package org.abc.framework.tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.SourcePositionComaparator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

public class DynamicDelimitedLineTokenizer extends DelimitedLineTokenizer implements InitializingBean
{
	private List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList;

	private String key;

	@Required
	public void setKey(String key_)
	{
		this.key = key_;
		
		setNames(getNames());
	}



	



	/**
	 * @return the sortedBySourcePositionInterfaceConfigurationBeanList
	 */
	public List<InterfaceConfigurationBean> getSortedBySourcePositionInterfaceConfigurationBeanList()
	{
		return sortedBySourcePositionInterfaceConfigurationBeanList;
	}







	/**
	 * @param sortedBySourcePositionInterfaceConfigurationBeanList the sortedBySourcePositionInterfaceConfigurationBeanList to set
	 */
	public void setSortedBySourcePositionInterfaceConfigurationBeanList(
			List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList)
	{
		this.sortedBySourcePositionInterfaceConfigurationBeanList = sortedBySourcePositionInterfaceConfigurationBeanList;
	}







	public void afterPropertiesSet() throws Exception
	{
		
	}

	
	
	private String[] getNames(){
		
		List<String> result = new ArrayList<String>();
		
		
		for (InterfaceConfigurationBean interfaceConfigurationBean : sortedBySourcePositionInterfaceConfigurationBeanList)
		{
			if (key.equals(interfaceConfigurationBean.getSource()))
			{	// set key as field id instead of Source column
				//result.add(interfaceConfigurationBean.getSourceColumn());
				result.add(interfaceConfigurationBean.getFieldId());
			}
		}
		String[] nameResult = new String[result.size()-1];
		return   result.toArray(nameResult);
		
	}
 
}
