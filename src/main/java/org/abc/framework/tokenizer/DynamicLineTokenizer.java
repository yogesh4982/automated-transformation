package org.abc.framework.tokenizer;

import java.util.List;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.springframework.batch.item.file.transform.LineTokenizer;

public interface DynamicLineTokenizer extends LineTokenizer {

	public void setKey(String key_);
	public void setSortedBySourcePositionInterfaceConfigurationBeanList(List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList);
}
