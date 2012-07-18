package org.abc.framework.blo;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.dao.InterfaceMasterDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("interfacemaster.xml")
public class InterfaceMasterBLOTest extends TestCase
{

	@Autowired
	protected InterfaceMasterBLO interfaceMasterBLO;

	@Autowired
	protected InterfaceMasterDAO interfaceMasterDAO;

	@Test
	public void testGetInterfaceConfigForName()
	{
		Map<String, String> result = interfaceMasterBLO.getInterfaceConfigurationForJobName("MERCH_CLASS");
		assertNotNull(result);
		assertEquals(result.get("INTERFACE_NAME"), "MERCH_CLASS");
		//assertEquals(result.get("INTERFACE_SUB_CODE"), "CLASS");
	}
	
	@Test
	public void testGetInterfaceConfigurationBeanList()
	{
		List<InterfaceConfigurationBean> list = interfaceMasterBLO.getInterfaceConfigurationBeanList(1);
		assertNotNull(list);
		
	}

	@Test
	public void testGetInterfaceConfigForInvalidName()
	{
		try
		{
			Map<String, String> result = interfaceMasterBLO.getInterfaceConfigurationForJobName("LOCATION");
			assertNull(result);
		}
		catch (IllegalArgumentException e)
		{
			assert (true);
			return;
		}
		// assert false;

	}

	/*@Test
	public void testUpdateVersionNumberForInterfaceNumber()
	{
		interfaceMasterDAO.updateVersionNumberForInterfaceNumber(101, 3);
		assert true;

	}

	@Test
	public void testUpdateVersionNumberForInterfaceNumberMaxVersion()
	{
		interfaceMasterBLO.updateVersionNumberForInterfaceNumber(101, 999, 999, 1);
		Map<String, String> updatedInterfaceRecord = interfaceMasterBLO.getInterfaceConfigurationForInterfaceNumber(101);
		assertEquals("1", updatedInterfaceRecord.get("VERSION_NUMBER"));
	}*/

}
