package org.abc.framework.writer;

import java.util.List;
import java.util.Map;

import org.abc.framework.bean.AttributesBean;
import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

public class MapJdbcItemWriter extends JdbcBatchItemWriter<Map<String, String>>
{
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;

	@Override
	public void setSql(String sql)
	{
		super.setSql(generateInsertStatement());
	}

	private String generateInsertStatement()
	{
		StringBuilder query = new StringBuilder();
		StringBuilder parameters = new StringBuilder();
		boolean tableNameAdded = false;
		for (InterfaceConfigurationBean interfaceConfigurationBean : interfaceConfigurationBeanList)
		{
			if (!tableNameAdded)
			{
				query.append("INSERT INTO " + interfaceConfigurationBean.getTarget() + " ( ");
				parameters.append(" values (");

			}
			if (!Constants.FILLER.equals(interfaceConfigurationBean.getTargetColumn()))
			{
				String targetColumn = interfaceConfigurationBean.getTargetColumn();
				if (targetColumn != null && !"".equals(targetColumn.trim()))
				{
					if (tableNameAdded)
					{
						query.append(",");
						parameters.append(",");
					}
					query.append(targetColumn);
					parameters.append("?");
				}
			}
			
			tableNameAdded = true;
		}
		query.append(")");
		parameters.append(")");
		query.append(parameters);
		return query.toString();

	}

	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	public void setInterfaceConfigurationBeanList(List<InterfaceConfigurationBean> interfaceConfigurationBeanList)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList;
	}
	@Override
	public void write(List<? extends Map<String, String>> items) throws Exception
	{
		for(Object obj : items)
		{
			System.out.println("===============================");
			super.write(((AttributesBean)obj).getListHashMap());
			System.out.println("===============================");
		}
		
	}
}
