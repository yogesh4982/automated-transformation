package org.abc.framework.bean;

import java.io.Serializable;

/******************************************************************************.
 * 
 * File Name 	: 	InterfaceConfigurationBean.java 
 * Description 	: 	Stores record of interface configuration 					 
 * @author 		: 	 
 * Date 		: 	04/05/2012 02:40:00 PM 
 * @version		: 	1.0
 *****************************************************************************/


public class InterfaceConfigurationBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4886504167643477155L;

	private String fieldId;

	private String interfaceId;
	
	private String fieldName;

	private String fieldDescription;
	
	private String fieldType;

	private String source;

	private String sourceColumn;
	
	private String target;

	private String targetColumn;
	
	private String truncateFlag;

	private String dataFormattingString;

	private String javaFormattingString;

	
	private String inputFieldLength;
	
	private String outputFieldLength;

	private String recordLevel;

	
	private String inputPosition;
	
	private String outputPosition;
	
	
	/**
	 * @return the fieldId
	 */
	public String getFieldId()
	{
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId)
	{
		this.fieldId = fieldId;
	}

	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId()
	{
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId)
	{
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldDescription
	 */
	public String getFieldDescription()
	{
		return fieldDescription;
	}

	/**
	 * @param fieldDescription the fieldDescription to set
	 */
	public void setFieldDescription(String fieldDescription)
	{
		this.fieldDescription = fieldDescription;
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType()
	{
		return fieldType;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(String fieldType)
	{
		this.fieldType = fieldType;
	}

	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * @return the sourceColumn
	 */
	public String getSourceColumn()
	{
		return sourceColumn;
	}

	/**
	 * @param sourceColumn the sourceColumn to set
	 */
	public void setSourceColumn(String sourceColumn)
	{
		this.sourceColumn = sourceColumn;
	}

	/**
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * @return the targetColumn
	 */
	public String getTargetColumn()
	{
		return targetColumn;
	}

	/**
	 * @param targetColumn the targetColumn to set
	 */
	public void setTargetColumn(String targetColumn)
	{
		this.targetColumn = targetColumn;
	}

	/**
	 * @return the truncateFlag
	 */
	public String getTruncateFlag()
	{
		return truncateFlag;
	}

	/**
	 * @param truncateFlag the truncateFlag to set
	 */
	public void setTruncateFlag(String truncateFlag)
	{
		this.truncateFlag = truncateFlag;
	}

	

	/**
	 * @return the dataFormattingString
	 */
	public String getDataFormattingString()
	{
		return dataFormattingString;
	}

	/**
	 * @param dataFormattingString the databaseFormattingString to set
	 */
	public void setDataFormattingString(String dataFormattingString)
	{
		this.dataFormattingString = dataFormattingString;
	}

	/**
	 * @return the javaFormattingString
	 */
	public String getJavaFormattingString()
	{
		return javaFormattingString;
	}

	/**
	 * @param javaFormattingString the javaFormattingString to set
	 */
	public void setJavaFormattingString(String javaFormattingString)
	{
		this.javaFormattingString = javaFormattingString;
	}

	

	/**
	 * @return the recordLevel
	 */
	public String getRecordLevel()
	{
		return recordLevel;
	}

	/**
	 * @param recordLevel the recordLevel to set
	 */
	public void setRecordLevel(String recordLevel)
	{
		this.recordLevel = recordLevel;
	}

	

	/**
	 * @return the alignmentIndicator
	 */
	public String getAlignmentIndicator()
	{
		return alignmentIndicator;
	}

	/**
	 * @param alignmentIndicator the alignmentIndicator to set
	 */
	public void setAlignmentIndicator(String alignmentIndicator)
	{
		this.alignmentIndicator = alignmentIndicator;
	}

	
	
	/**
	 * @return the inputPosition
	 */
	public String getInputPosition()
	{
		return inputPosition;
	}

	/**
	 * @param inputPosition the inputPosition to set
	 */
	public void setInputPosition(String inputPosition)
	{
		this.inputPosition = inputPosition;
	}

	/**
	 * @return the outputPosition
	 */
	public String getOutputPosition()
	{
		return outputPosition;
	}

	/**
	 * @param outputPosition the outputPosition to set
	 */
	public void setOutputPosition(String outputPosition)
	{
		this.outputPosition = outputPosition;
	}

	private String alignmentIndicator;

	/**
	 * @return the inputFieldLength
	 */
	public String getInputFieldLength()
	{
		return inputFieldLength;
	}

	/**
	 * @param inputFieldLength the inputFieldLength to set
	 */
	public void setInputFieldLength(String inputFieldLength)
	{
		this.inputFieldLength = inputFieldLength;
	}

	/**
	 * @return the outputFieldLength
	 */
	public String getOutputFieldLength()
	{
		return outputFieldLength;
	}

	/**
	 * @param outputFieldLength the outputFieldLength to set
	 */
	public void setOutputFieldLength(String outputFieldLength)
	{
		this.outputFieldLength = outputFieldLength;
	}

	

}
