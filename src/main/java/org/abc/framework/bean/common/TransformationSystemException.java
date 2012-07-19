
package org.abc.framework.bean.common;

/******************************************************************************.
 * File Name        : ApparelSystemException.java
 * Description      : It logs the exception thrown with the message and data. 
 * @author          : TCS
 * Date             : 25/6/2012 05:30:00 PM
 * @version         : 1.0
 *****************************************************************************/
public class TransformationSystemException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	private String data;

	/**
	 * This method logs the exception thrown with the message and data.
	 * 
	 * @param message_
	 *            String
	 * @param data_
	 *            String
	 */
	public TransformationSystemException(final String message_, final String data_)
	{
		super(message_);
		this.setData(data_);
	}
	
	/**
	 * This method logs the exception thrown with the message and data.
	 * 
	 * @param message_
	 *            String
	 */
	public TransformationSystemException(final String message_ )
	{
		super(message_);
		
	}

	/**
	 * @return the data
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * @param data_ the data to set
	 */
	public void setData(final String data_)
	{
		this.data = data_;
	}

	
}
