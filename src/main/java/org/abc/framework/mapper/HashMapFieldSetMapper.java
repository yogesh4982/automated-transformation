package org.abc.framework.mapper;

import java.util.HashMap;
import java.util.Map;

import org.abc.framework.bean.common.Constants;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/******************************************************************************
*
* File Name        : HashMapRowMapper.java
* Description      : Maps the values from ResultSet to Map. 
* @author          : 
* Date             : 26/3/2012 04:40:00 PM
* @version         : 1.0
* 
*****************************************************************************/

public class HashMapFieldSetMapper implements FieldSetMapper<Map<String, String>>
{

	private String level; 
	
	public Map<String, String> mapFieldSet(FieldSet fieldSet) throws BindException
	{
		Map<String, String> result = new HashMap<String, String>();
		String[] names = fieldSet.getNames();
		for(String name:names)
		{
			result.put(name,fieldSet.readString(name));
		}
		result.put(Constants.LEVEL,level);
		return result;
	}

	/**
	 * @return the level
	 */
	public String getLevel()
	{
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}

	
	

	

}
