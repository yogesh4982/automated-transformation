package org.abc.framework.bean.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
/******************************************************************************
*
* File Name        : StringAndDateConversion.java
* Description      : Converts Date to String and String to Date.
* @author          : TCS
* Date             : 26/3/2012 07:20:00 PM
* @version         : 1.0
* 
*****************************************************************************/
public final class StringAndDateConversion
{
	/**
	 * Constructor for class.
	 */
	private StringAndDateConversion(){
		super();
	}
	/**
	 * Gets the Date object by passing string and date format.
	 * 
	 * @param dateFormat_
	 *            String
	 * @param testDate_
	 *            String
	 * @return Date Returns date
	 * @exception ParseException
	 *                throws when String cannot be parsed into Date.
	 */
	public static Date getDate(final String dateFormat_,final  String testDate_) throws ParseException
	{

		final DateFormat formatter;
		final Date date;
		formatter = new SimpleDateFormat(dateFormat_);
		date = (Date) formatter.parse(testDate_);
		return date;

	}

	/**
	 * Gets String by passing Date and date format.
	 * 
	 * @param dateFormat_
	 *            String
	 * @param date_
	 *            Date
	 * @return String Returns String of date.
	 */
	public static String getStringDate(final String dateFormat_,final  Date date_)
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat_);
		return formatter.format(date_);

	}
	/**
	 * Format the input string.
	 * @param input_
	 * 			Object
	 * @param format_
	 * 			String
	 * @return String
	 */
	public static String formatString(final Object input_,final  String format_)
	{
		final Formatter fmt = new  Formatter();
		return fmt.format(format_, input_).toString();
	}
	/**
	 *Used to replace string.
	 * @param inputNew_
	 * 			String
	 * @param replace_
	 * 			String
	 * @param newReplace_
	 * 			String
	 * @return String
	 */
	public static String replaceAll(final String inputNew_, final String replace_, final String newReplace_){
		String input = inputNew_ ;
		while (input.indexOf(replace_)!= -1){
			input = input.replace(replace_, newReplace_);
		}
		return input;
	}
	
	/**
	 * Format the input string.
	 * @param input_
	 * 			Object
	 * @param format_
	 * 			String
	 * @return String
	 */
	public static String formatDoubleValue(final double input_,final  String format_)
	{
		final NumberFormat formatter = new DecimalFormat(format_);
		return formatter.format(input_);
	}
	
	

	/**
	 * This function returns values.
	 * 
	 * from the property file
	 * 
	 * @param propertyKey_
	 *            propertyKey_
	 * @param resourceBundleName_
	 *            resourceBundleName_
	 * @return String
	 */
	public static String getValueFromResourceBundle(final String propertyKey_, final String resourceBundleName_)
	{
		final ResourceBundle rb = ResourceBundle.getBundle(resourceBundleName_);
		return rb.getString(propertyKey_);
	}
	
	/***
	 * This method converts the GregorianCalendar to XMLGregorianCalendar.
	 * @param calendar_ Calendar
	 * @return XMLGregorianCalendar XMLGregorianCalendar
	 * @throws DatatypeConfigurationException DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar convertToGregorianCalendar(final Calendar calendar_) 
			throws DatatypeConfigurationException
	{
		if (calendar_ instanceof GregorianCalendar)
		{
			return DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)calendar_);
		}
		else
		{
			throw new IllegalArgumentException("Expects Gregorian calendar");
		}
	}
	
	/**
	 * This method uses reflection to invoke java method.
	 * @param classNameToInvoke_ classNameToInvoke_
	 * @param methodName_ methodName_
	 * @param methodParameters_ methodParameters_
	 * @return String
	 */
	public static String invokeJavaMethodToFormatString(final String classNameToInvoke_, final String methodName_,
			final Object[] methodParameters_) 
	{
		try {
			final Class javaClassForClassName = Class.forName(classNameToInvoke_);

			final Object javaObjectForClassName = javaClassForClassName.newInstance();

			final List<Class> methodParametersType = new ArrayList<Class>();
			for (Object methodParameter : methodParameters_)
			{
				methodParametersType.add(methodParameter.getClass());
			}
			final Class[] methodParametersTypeArray = methodParametersType
					.toArray(new Class[methodParametersType.size()]);

			final Method javaMethodForMethodName = javaClassForClassName.getDeclaredMethod(methodName_,
					methodParametersTypeArray);
			final String stringAttributeDataForFile = (String) javaMethodForMethodName.invoke(javaObjectForClassName,
					methodParameters_);
			return stringAttributeDataForFile;
		}catch (ClassNotFoundException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (SecurityException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (NoSuchMethodException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			throw new TransformationException(e.getMessage());
		}
		catch (InvocationTargetException e)
		{
			if (e != null && e.getCause() != null && e.getCause().getMessage() != null)
			{
				if(e.getCause() instanceof TransformationException)
				{
					throw (TransformationException)e.getCause();
				}
				else
				{
					throw new TransformationException(e.getCause().getMessage());
				}
			}
		}
		return null;
		
	}
}
