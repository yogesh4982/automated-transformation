package org.abc.framework.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.SourcePositionComaparator;
import org.abc.framework.tokenizer.DynamicDelimitedLineTokenizer;
import org.abc.framework.tokenizer.DynamicFixedLengthTokenizer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class DynamicPatternMatchingCompositeLineMapper implements LineMapper
{
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;
	
	private PatternMatchingCompositeLineMapper<Map<String, String>> delegateLineMapper;
	
	private String delimiter;

	/**
	 * This method configures 
	 * List of Tokenizers
	 * List of Field set mappers
	 * @throws Exception
	 */
	public void configureDynamicPatternMatchingCompositeLineMapper() throws Exception
	{
	//
		// Create tokenizers for distinct source
		// set source as key for the tokenizer
		delegateLineMapper = new PatternMatchingCompositeLineMapper<Map<String,String>>();
		List<InterfaceConfigurationBean> sortedBySourcePosition = new ArrayList<InterfaceConfigurationBean>(interfaceConfigurationBeanList.size());
		sortedBySourcePosition.addAll(interfaceConfigurationBeanList);
		Collections.copy(sortedBySourcePosition, interfaceConfigurationBeanList);
		
		// Sort the interface configuration bean list by record level then position
		Collections.sort(sortedBySourcePosition, new SourcePositionComaparator());
		
		String previousSource = null;
		String currentSource = null;
		Map<String, LineTokenizer> tokenizers =  new HashMap<String, LineTokenizer>();
		Map<String, FieldSetMapper<Map<String, String>>> fieldSetMappers = new HashMap<String, FieldSetMapper<Map<String, String>>>();
		
		// Create a new set of Tokenizer and Field set mapper for each different
		// type of source
		// Source should contain record identifier for particular row of in a file 
		for (InterfaceConfigurationBean interfaceConfigurationBean : sortedBySourcePosition)
		{
			currentSource = interfaceConfigurationBean.getSource();
			if (!currentSource.equals(previousSource))
			{
				LineTokenizer tokenizer = null;
				if (delimiter == null){
					tokenizer = new DynamicFixedLengthTokenizer();
					((DynamicFixedLengthTokenizer)tokenizer).setSortedBySourcePositionInterfaceConfigurationBeanList(sortedBySourcePosition);
					((DynamicFixedLengthTokenizer)tokenizer).setKey(currentSource);
				} else { 
					tokenizer = new DynamicDelimitedLineTokenizer();
					((DynamicDelimitedLineTokenizer)tokenizer).setDelimiter(delimiter.toCharArray()[0]);
					((DynamicDelimitedLineTokenizer)tokenizer).setSortedBySourcePositionInterfaceConfigurationBeanList(sortedBySourcePosition);
					((DynamicDelimitedLineTokenizer)tokenizer).setKey(currentSource);
				}
		
				HashMapFieldSetMapper fieldSetMapper = new HashMapFieldSetMapper();
				fieldSetMapper.setLevel(interfaceConfigurationBean.getRecordLevel());
				
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

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
