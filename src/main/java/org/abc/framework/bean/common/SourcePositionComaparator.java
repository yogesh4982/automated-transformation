package org.abc.framework.bean.common;

import java.util.Comparator;

import org.abc.framework.bean.InterfaceConfigurationBean;

public class SourcePositionComaparator implements Comparator<InterfaceConfigurationBean> 
{

	public int compare(InterfaceConfigurationBean bean1, InterfaceConfigurationBean bean2)
	{
		if (Integer.parseInt(bean1.getRecordLevel()) > Integer.parseInt(bean2.getRecordLevel()))
		{
			return 1;
		} 
		else if (Integer.parseInt(bean1.getRecordLevel()) < Integer.parseInt(bean2.getRecordLevel()))
		{
			return -1;
		}
		else if (Integer.parseInt(bean1.getInputPosition()) > Integer.parseInt(bean2.getInputPosition()))
		{
			return 1;
		} else if (Integer.parseInt(bean1.getInputPosition()) < Integer.parseInt(bean2.getInputPosition()))
		{
			return -1;
		}
			
		return 0;
	}

}
