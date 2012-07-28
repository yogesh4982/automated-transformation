package org.abc.framework.writter;

import java.util.List;

import org.abc.framework.bean.AttributesBean;
import org.springframework.batch.item.ItemWriter;

public class DummyWritter implements ItemWriter<Object> {

	

	

	public void write(List<? extends Object> items) throws Exception
	{
		System.out.println("items = "+items);
		for(Object obj : items)
		{
			System.out.println("===============================");
			System.out.println(((AttributesBean)obj).getListHashMap());
			System.out.println("===============================");
		}
		
	}

	
}
