package org.abc.framework.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.abc.framework.bean.common.AttributesBean;
import org.junit.Test;

public class AttributesBeanTest extends TestCase
{

	@Test
	public void testGetListHashMap(){
		AttributesBean bean1= new AttributesBean();
		List<AttributesBean> var = new ArrayList<AttributesBean>();
		AttributesBean bean21= new AttributesBean();
		bean21.setAttribute("21", "21");
		AttributesBean bean22= new AttributesBean();
		bean22.setAttribute("22", "22");
		var.add(bean21);
		var.add(bean22);
		bean1.setVarryingFields(var);
		
		List<AttributesBean> var1 = new ArrayList<AttributesBean>();
		AttributesBean bean31= new AttributesBean();
		bean31.setAttribute("31", "31");
		AttributesBean bean32= new AttributesBean();
		bean32.setAttribute("32", "32");
		var1.add(bean31);
		var1.add(bean32);
		bean21.setVarryingFields(var1);
		
		bean1.setAttribute("1", "1");
		
		System.out.println(bean1.getListHashMap());
	}

}
