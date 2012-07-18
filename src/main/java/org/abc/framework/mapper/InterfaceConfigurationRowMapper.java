package org.abc.framework.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.springframework.jdbc.core.RowMapper;

/******************************************************************************
*
* File Name        : InterfaceConfigurationRowMapper.java
* Description      : Maps for the values from the Database Table to the bean. 
* @author          : 
* Date             : 26/3/2012 06:40:00 PM
* @version         : 1.0
* 
*****************************************************************************/

public class InterfaceConfigurationRowMapper implements RowMapper<InterfaceConfigurationBean>
{
	/**
	 * Maps the values of the file to the bean.
	 * 
	 * @param resultSet_
	 *            ResultSet
	 * @param rowNumber_
	 *            int
	 * @return InterfaceConfigurationBean Returns the bean with values populated
	 *         in it.
	 * @exception SQLException
	 *                thrown if error occurs while reading data from the
	 *                database.
	 */
	
	public InterfaceConfigurationBean mapRow(final ResultSet resultSet_,final int rowNumber_) throws SQLException
	{
		final InterfaceConfigurationBean attributeConfigBean = new InterfaceConfigurationBean();
		attributeConfigBean.setFieldId(resultSet_.getString("FIELD_ID"));
		attributeConfigBean.setInterfaceId(resultSet_.getString("INTERFACE_ID"));
		attributeConfigBean.setFieldName(resultSet_.getString("FIELD_NAME"));
		attributeConfigBean.setFieldType(resultSet_.getString("FIELD_TYPE"));
		attributeConfigBean.setFieldDescription(resultSet_.getString("FIELD_DESCRIPTION"));
		attributeConfigBean.setSourceColumn(resultSet_.getString("SOURCE_COLUMN"));
		attributeConfigBean.setSource(resultSet_.getString("SOURCE"));
		attributeConfigBean.setTargetColumn(resultSet_.getString("TARGET_COLUMN"));
		attributeConfigBean.setTarget(resultSet_.getString("TARGET"));
		attributeConfigBean.setTruncateFlag(resultSet_.getString("TRUNCATE_FLAG"));
		attributeConfigBean.setDataFormattingString(resultSet_.getString("DATA_FORMATTING_STRING"));
		attributeConfigBean.setJavaFormattingString(resultSet_.getString("JAVA_FORMATTING_STRING"));
		attributeConfigBean.setInputFieldLength(resultSet_.getString("INPUT_FIELD_LENGTH"));
		attributeConfigBean.setOutputFieldLength(resultSet_.getString("OUTPUT_FIELD_LENGTH"));
		attributeConfigBean.setRecordLevel(resultSet_.getString("RECORD_LEVEL"));
		attributeConfigBean.setInputPosition(resultSet_.getString("INPUT_POSITION"));
		attributeConfigBean.setOutputPosition(resultSet_.getString("OUTPUT_POSITION"));
		attributeConfigBean.setAlignmentIndicator(resultSet_.getString("ALIGNMENT_IND"));

		return attributeConfigBean;
	}

}
