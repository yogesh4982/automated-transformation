package org.abc.framework.writter;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class DummyWritter implements ItemWriter<Object> {

	

	

	public void write(List<? extends Object> items) throws Exception
	{
		System.out.println("items = "+items.size());
		
	}

	
}
