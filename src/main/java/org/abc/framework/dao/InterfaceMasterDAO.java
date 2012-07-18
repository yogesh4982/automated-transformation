package org.abc.framework.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.mapper.HashMapRowMapper;
import org.abc.framework.mapper.InterfaceConfigurationRowMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

/******************************************************************************.
*
* File Name        : InterfaceMasterDAO.java
* Description      : Gets the values from the database through queries. 
* @author          : 
* Date             : 26/3/2012 05:30:00 PM
* @version         : 1.0
* 
*****************************************************************************/

public class InterfaceMasterDAO
{

	private JdbcTemplate jdbcTemplate;

	/**
	 * Gets the values from the INTERFACE_MASTER Table by passing interface name.
	 * 
	 * @param interfaceName_
	 *            String
	 * @return Map Returns the map with fields as the key.
	 */
	public List<Map<String, String>> getInterfaceConfigurationForJobName(final String interfaceName_)
	{
		final String[] parameterArray = new String[] { interfaceName_.toUpperCase() };
		final StringBuilder stringBuilder = new StringBuilder(47);
		stringBuilder.append("SELECT INTERFACE_ID, ");
		stringBuilder.append("INTERFACE_TYPE, INTERFACE_NAME, INTERFACE_DESCRIPTION, ");
		stringBuilder.append("INPUTFILE_NAME_FORMAT, OUTPUTFILE_NAME_FORMAT, ");
		stringBuilder.append("INPUTFILE_NAME_FORMAT, OUTPUTFILE_NAME_FORMAT, ");
		stringBuilder.append("INPUTFILE_VERSION_NUMBER ,OUTPUTFILE_VERSION_NUMBER, ");
		stringBuilder.append("MIN_VERSION_NUMBER, MAX_VERSION_NUMBER, ");
		stringBuilder.append("TRAILER_VALIDATION_FLAG, HEADER_VALIDATION_FLAG ");
		stringBuilder.append("FROM INTERFACE_MASTER ");
		stringBuilder.append("WHERE UPPER(INTERFACE_NAME)=? ");
		final List<Map<String, String>> returnList = jdbcTemplate.query(stringBuilder.toString(),
				parameterArray, new HashMapRowMapper());
		return returnList;
	}

	

	/**
	 * Gets the List of beans of Interface Configuration.
	 * 
	 * @param interfaceNumber_
	 *            int
	 * @return List List of Interface Configuration beans.
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList(final int interfaceNumber_)
	{
		List<InterfaceConfigurationBean> interfaceConfigurationBeanList = new ArrayList<InterfaceConfigurationBean>();
		final Integer[] parameterArray = new Integer[] { interfaceNumber_ };
		final StringBuilder stringBuilder = new StringBuilder(47);
		stringBuilder.append("SELECT FIELD_ID,INTERFACE_ID,FIELD_NAME,FIELD_DESCRIPTION,FIELD_TYPE,");
		stringBuilder.append("SOURCE_COLUMN,SOURCE,");
		stringBuilder.append("TARGET_COLUMN,TARGET,");
		stringBuilder.append("DATA_FORMATTING_STRING,JAVA_FORMATTING_STRING,TRUNCATE_FLAG,");
		stringBuilder.append("INPUT_FIELD_LENGTH,OUTPUT_FIELD_LENGTH, INPUT_POSITION, OUTPUT_POSITION,");
		stringBuilder.append("ALIGNMENT_IND, RECORD_LEVEL ");
		stringBuilder.append("FROM INTERFACE_CONFIGURATION ");
		stringBuilder.append("WHERE INTERFACE_ID = ? ");
		stringBuilder.append("ORDER BY RECORD_LEVEL,OUTPUT_POSITION ");
		interfaceConfigurationBeanList = jdbcTemplate.query(stringBuilder.toString(), parameterArray,
				new InterfaceConfigurationRowMapper());
		return interfaceConfigurationBeanList;
	}
	
	/**
	 * Gets the List of Map for Max of Record Level for Interface Configuration.
	 * 
	 * @param interfaceNumber_
	 *            int
	 * @return List of Map for Max record Level.
	 */
	public List<Map<String, String>> getInterfaceConfigurationMaximumLevel(final int interfaceNumber_)
	{
		final Integer[] parameterArray = new Integer[] { interfaceNumber_ };
		final StringBuilder stringBuilder = new StringBuilder(47);
		stringBuilder.append("SELECT MAX(RECORD_LEVEL) AS MAX_RECORD_LEVEL ");
		stringBuilder.append("FROM INTERFACE_CONFIGURATION ");
		stringBuilder.append("WHERE INTERFACE_CONFIGURATION.INTERFACE_ID = ? ");
		final List<Map<String, String>> returnList = jdbcTemplate.query(stringBuilder.toString(),
				parameterArray, new HashMapRowMapper());
		return returnList;
	}

	/**
	 * Update the version number into the database.
	 * 
	 * @param interfaceNumber_
	 *            int
	 * @param versionNumber_
	 *            int
	 */
	public void updateVersionNumberForInterfaceNumber(final int interfaceNumber_, final int versionNumber_, boolean isInputVersion)
	{
		final Integer[] parameterArray = new Integer[] { versionNumber_, interfaceNumber_ };
		StringBuilder query = new StringBuilder();
		query.append("UPDATE INTERFACE_MASTER SET ");
		if (isInputVersion)
		{
			query.append("INPUT_VERSION_NUMBER");
		} else
		{
			query.append("OUTPUT_VERSION_NUMBER");
		}
		query.append(" = ? WHERE INTERFACE_ID = ? ");
		jdbcTemplate.update(query.toString(),
				(Object[]) parameterArray);
	}

	 

	 

	/**
	 * @param dataSource_
	 *            the DataSource to set
	 */
	@Required
	public void setDataSource(final DataSource dataSource_)
	{
		this.setJdbcTemplate(new JdbcTemplate(dataSource_));
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate_
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate_)
	{
		this.jdbcTemplate = jdbcTemplate_;
	}

}
