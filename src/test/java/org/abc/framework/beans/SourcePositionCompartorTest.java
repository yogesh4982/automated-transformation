package org.abc.framework.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.SourcePositionComaparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SourcePositionCompartorTest extends TestCase
{

	@Test
	public void testSort(){
		List<InterfaceConfigurationBean> input = new ArrayList<InterfaceConfigurationBean>();
		InterfaceConfigurationBean bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("1");
		bean.setInputPosition("3");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("1");
		bean.setInputPosition("1");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("1");
		bean.setInputPosition("5");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("2");
		bean.setInputPosition("1");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("2");
		bean.setInputPosition("5");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("2");
		bean.setInputPosition("2");
		input.add(bean);
		bean = new InterfaceConfigurationBean();
		bean.setRecordLevel("1");
		bean.setInputPosition("2");
		input.add(bean);
		Collections.sort(input, new SourcePositionComaparator());
		assertEquals(input.get(input.size()-1).getInputPosition(), "5");
	}

}
