package org.abc.framework.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.SourcePositionComaparator;
import org.abc.framework.tokenizer.DynamicFixedLengthTokenizer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class DynamicPatternMatchingCompositeLineMapper implements LineMapper
{
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;
	
	private PatternMatchingCompositeLineMapper<Map<String, String>> delegateLineMapper;

	public void configureDynamicPatternMatchingCompositeLineMapper() throws Exception
	{
	//
		// Create tokenizers for distinct source
		// set source as key for the tokenizer
		delegateLineMapper = new PatternMatchingCompositeLineMapper<Map<String,String>>();
		List<InterfaceConfigurationBean> sortedBySourcePosition = new ArrayList<InterfaceConfigurationBean>(interfaceConfigurationBeanList.size());
		sortedBySourcePosition.addAll(interfaceConfigurationBeanList);
		Collections.copy(sortedBySourcePosition, interfaceConfigurationBeanList);
		Collections.sort(sortedBySourcePosition, new SourcePositionComaparator());
		String previousSource = null;
		String currentSource = null;
		Map<String, LineTokenizer> tokenizers = new HashMap<String, LineTokenizer>();
		Map<String, FieldSetMapper<Map<String, String>>> fieldSetMappers = new HashMap<String, FieldSetMapper<Map<String, String>>>();
		for (InterfaceConfigurationBean interfaceConfigurationBean : sortedBySourcePosition)
		{
			currentSource = interfaceConfigurationBean.getSource();
			if (!currentSource.equals(previousSource))
			{
				DynamicFixedLengthTokenizer tokenizer = new DynamicFixedLengthTokenizer();
				HashMapFieldSetMapper fieldSetMapper = new HashMapFieldSetMapper();
				fieldSetMapper.setLevel(interfaceConfigurationBean.getRecordLevel());
				tokenizer.setSortedBySourcePositionInterfaceConfigurationBeanList(sortedBySourcePosition);
				tokenizer.setKey(currentSource);
				tokenizers.put(currentSource, tokenizer);
				fieldSetMappers.put(currentSource,fieldSetMapper);
				previousSource = currentSource;
				
			}
		}
		delegateLineMapper.setFieldSetMappers(fieldSetMappers);
		delegateLineMapper.setTokenizers(tokenizers);
		delegateLineMapper.afterPropertiesSet();
	}

	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	public void setInterfaceConfigurationBeanList(List<InterfaceConfigurationBean> interfaceConfigurationBeanList) throws Exception
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList;
		configureDynamicPatternMatchingCompositeLineMapper();
	}

	

	public Object mapLine(String line, int lineNumber) throws Exception
	{
		return delegateLineMapper.mapLine(line, lineNumber);
	}
}
