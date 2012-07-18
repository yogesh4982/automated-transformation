package org.abc.framework.blo;

import java.util.List;
import java.util.Map;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.dao.InterfaceMasterDAO;

/******************************************************************************.
*
* File Name        : InterfaceMasterBLO.java
* Description      : Calls the methods of DAO based on the arguments passed. 
* @author          : 
* Date             : 26/3/2012 04:40:00 PM
* @version         : 1.0
* 
*****************************************************************************/
public class InterfaceMasterBLO
{

	private InterfaceMasterDAO interfaceMasterDAO;

	/**
	 * Checks and Update the version number into the database.
	 * 
	 * @param interfaceNumber_
	 *            int
	 * @param currentVersionNumber_
	 *            int
	 * @param maximumVersionNumber_
	 *            int
	 * @param minimumVersionNumber_
	 *            int
	 * @param isInputVersion
	 *            boolean
	 */
	public void updateVersionNumberForInterfaceNumber(final int interfaceNumber_,final int currentVersionNumber_,
			final int maximumVersionNumber_,final int minimumVersionNumber_,boolean isInputVersion)
	{
		int currentVersionNumber=currentVersionNumber_;
		if (currentVersionNumber_ == maximumVersionNumber_)
		{
			interfaceMasterDAO.updateVersionNumberForInterfaceNumber(interfaceNumber_, minimumVersionNumber_,isInputVersion);
		}
		else
		{
			interfaceMasterDAO.updateVersionNumberForInterfaceNumber(interfaceNumber_, ++currentVersionNumber,isInputVersion);
		}
	}

	/**
	 * Gets the values from the Interface Master Table by passing JobName.
	 * @param interfaceName_
	 *            String
	 * @return Map Returns the map with fields as the key.
	 */
	public Map<String, String> getInterfaceConfigurationForJobName(final String interfaceName_)
	{
		final List<Map<String, String>> resultList = interfaceMasterDAO
				.getInterfaceConfigurationForJobName(interfaceName_);
		if (resultList.size() > 1)
		{
			throw new IllegalArgumentException("Argument passed does not result unique result Interface Code => "+
					interfaceName_);
		}
		else if (resultList.size() == 1)
		{
			return resultList.get(0);
		}
		else
		{
			throw new IllegalArgumentException("Argument passed does not result match Interface Code => "+
					interfaceName_);
		}

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
		return interfaceMasterDAO.getInterfaceConfigurationBeanList(interfaceNumber_);

	}
	
	/**
	 * Gets the List of Max of Record Level for Interface Configuration.
	 * 
	 * @param interfaceNumber_
	 *            int
	 * @return List Map of Max record Level.
	 */
	public List<Map<String, String>> getInterfaceConfigurationMaximumLevel(final int interfaceNumber_)
	{
		return interfaceMasterDAO.getInterfaceConfigurationMaximumLevel(interfaceNumber_);

	}

	

	/**
	 * @return the interfaceMasterDAO
	 */
	public InterfaceMasterDAO getInterfaceMasterDAO()
	{
		return interfaceMasterDAO;
	}

	/**
	 * @param interfaceMasterDAO_
	 *            the interfaceMasterDAO to set
	 */
	public void setInterfaceMasterDAO(final InterfaceMasterDAO interfaceMasterDAO_)
	{
		this.interfaceMasterDAO = interfaceMasterDAO_;
	}

}
