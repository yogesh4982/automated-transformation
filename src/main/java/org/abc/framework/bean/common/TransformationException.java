package org.abc.framework.bean.common;

public class TransformationException extends RuntimeException
{
	private String data;

	/**
	 * This method logs the exception thrown with the message and data.
	 * 
	 * @param message_
	 *            String
	 * @param data_
	 *            String
	 */
	public TransformationException(final String message_, final String data_)
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
	public TransformationException(final String message_ )
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
