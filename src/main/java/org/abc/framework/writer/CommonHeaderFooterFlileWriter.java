package org.abc.framework.writer;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.abc.framework.bean.common.StringAndDateConversion;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;

/******************************************************************************
 * File Name   : CommonHeaderFooterFlatFileWriter.java 
 * Description : Uses the methods of Tasklet for preparing Header footer.
 * @author     : TCS 
 * Date        : 29/3/2012 08:26:00 PM
 * @version    : 1.0
 *****************************************************************************/
public class CommonHeaderFooterFlileWriter extends FlatFileItemWriter<List<String>> implements
		FlatFileFooterCallback, FlatFileHeaderCallback
{


	

	




	private StepExecution stepExecution;

	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;
	
	private boolean isMultilevel;
	
	
	/**
	 * @return the isMultilevel
	 */
	public boolean getIsMultilevel() {
		return isMultilevel;
	}

	/**
	 * @param isMultilevel_ the isMultilevel to set
	 */
	public void setIsMultilevel(final boolean isMultilevel_) {
		this.isMultilevel = isMultilevel_;
	}

	/**
	 * @return the interfaceConfigurationBeanList
	 */
	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	/**
	 * @param interfaceConfigurationBeanList_
	 *            the interfaceConfigurationBeanList to set
	 */
	public void setInterfaceConfigurationBeanList(final List<InterfaceConfigurationBean> 
													interfaceConfigurationBeanList_)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList_;
	}

	/**
	 * This method execute before step.
	 * 
	 * @param stepExecution_
	 *            StepExecution
	 */
	@BeforeStep
	public void beforeStep(final StepExecution stepExecution_)
	{
		this.stepExecution = stepExecution_;
		
	}

	

	

	/**
	 * This method is used to wirte Header.
	 * 
	 * @param writer_
	 *            Writer
	 * @throws IOException
	 *             Exception
	 */
	public void writeHeader(final Writer writer_) throws IOException
	{
		final StringBuilder headerStringBuilder = new StringBuilder(1024);
		for(InterfaceConfigurationBean interfaceConfigurationBean: interfaceConfigurationBeanList)
		{
			if (Constants.FIELD_TYPE_HEADER_RECORD.equals(interfaceConfigurationBean.getFieldType()))
			{
				headerStringBuilder.append(processHeaderFooter(interfaceConfigurationBean));
				
			}
			
		}
		writer_.write(headerStringBuilder.toString());
		
	}

	/**
	 * This method is used to write records .
	 * 
	 * @param items_
	 *            List
	 * @throws Exception
	 *             Exception
	 * @throws NumberFormatException
	 *             NumberFormatException
	 */
	public void write(final List<? extends List<String>> items_) throws Exception, NumberFormatException
	{
		
			super.write(items_);
		
	}

	

	
	
	

	

	
	/**
	 * This method applies java formatting.
	 * @param formattingString_
	 * 		String
	 * @param methodParameters_
	 *		String
	 * @return String
	 */
	private String applyJavaFormatting(final String formattingString_,final  Object... methodParameters_)
	{
		final String[] javaArguments = formattingString_.split(":");
		final String formattingClassName = javaArguments[0];
		final String formattingMethodName = javaArguments[1];
		return StringAndDateConversion.invokeJavaMethodToFormatString(formattingClassName, formattingMethodName,
				methodParameters_);
	}
	
	/**
	 * This method applies both java as well as string formatting.
	 * @param orginalValue_ Object
	 * @param headerFooterAttributesBean_ HeaderFooterAttributesBean
	 * @return String
	 */
	private String doFormatting(final Object orginalValue_,final InterfaceConfigurationBean interfaceConfigurationBean)
	{
		final String formattingString = interfaceConfigurationBean.getDataFormattingString();
		final String javaFormattingString = interfaceConfigurationBean.getJavaFormattingString();
		String result = null;
		if (formattingString != null)
		{
			if (formattingString.contains("d"))
			{
				result = StringAndDateConversion.formatString(Long.parseLong(orginalValue_.toString()), formattingString);
			} else 
			{
				result = StringAndDateConversion.formatString(orginalValue_, formattingString);
			}
			
		}
		else
		{
			result = orginalValue_ + Constants.BLANK_SPACE;
		}
		if (javaFormattingString != null && !Constants.BLANK_SPACE.equals(javaFormattingString))
		{
			result = applyJavaFormatting(javaFormattingString, result);

		}
		return result;
	}

	public void writeFooter(Writer writer) throws IOException {
		final StringBuilder headerStringBuilder = new StringBuilder(1024);
		for(InterfaceConfigurationBean interfaceConfigurationBean: interfaceConfigurationBeanList)
		{
			if (Constants.FIELD_TYPE_TRAILER_RECORD.equals(interfaceConfigurationBean.getFieldType()))
			{
				headerStringBuilder.append(processHeaderFooter(interfaceConfigurationBean));
				
			}
			
		}
		writer.write(headerStringBuilder.toString());
		
	}
	
	private String processHeaderFooter(InterfaceConfigurationBean interfaceConfigurationBean)
	{
		String result = null;
		if ("JOB_EXECUTION_CONTEXT".equals(interfaceConfigurationBean.getSource()))
		{
			final Object dataFromJobExecutionContext = stepExecution.getJobExecution().getExecutionContext()
					.get(interfaceConfigurationBean.getFieldName()).toString();
			result = doFormatting(dataFromJobExecutionContext,
					interfaceConfigurationBean);
		}
		else if("JOB_PARAMETERS".equals(interfaceConfigurationBean.getSource()))
		{
			final Object dataFromJobParameter = stepExecution.getJobParameters().getParameters()
					.get(interfaceConfigurationBean.getFieldName()).toString();
			result = doFormatting(dataFromJobParameter,
					interfaceConfigurationBean);
		}
		else
		{
			result = doFormatting(interfaceConfigurationBean.getSourceColumn(),
					interfaceConfigurationBean);
		}
		return result;
	}

}
