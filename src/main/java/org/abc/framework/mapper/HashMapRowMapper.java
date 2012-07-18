package org.abc.framework.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/******************************************************************************
*
* File Name        : HashMapRowMapper.java
* Description      : Maps the values from ResultSet to Map. 
* @author          : 
* Date             : 26/3/2012 04:40:00 PM
* @version         : 1.0
* 
*****************************************************************************/

public class HashMapRowMapper implements RowMapper<Map<String, String>>
{

	/**
	 * Maps the values fetched from the database to Hash Map with column
	 * name as the key and value as content.
	 * 
	 * @param resultSet_
	 *            ResultSet
	 * @param argument1_
	 *            int
	 * @return Map
	 * @throws SQLException
	 * 			  SQLException
	 */
	public Map<String, String> mapRow(final ResultSet resultSet_,final int argument1_) throws SQLException
	{
		final Map<String, String> results = new HashMap<String, String>();
		final int numberOfColumns = resultSet_.getMetaData().getColumnCount();
		final ResultSetMetaData resultSetMetaData = resultSet_.getMetaData();
		for (int counter = 1; counter <= numberOfColumns; counter++)
		{
			results.put(resultSetMetaData.getColumnLabel(counter).toUpperCase().trim(),
					resultSet_.getString(resultSetMetaData.getColumnLabel(counter)));
		}

		return results;
	}

}
