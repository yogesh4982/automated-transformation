package org.abc.framework.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.abc.framework.bean.InterfaceConfigurationBean;
import org.abc.framework.bean.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class MapPreparedStatementSetter
		implements
			ItemPreparedStatementSetter<Map<String, String>> {
	// contains field id as key and corresponding Configuration record as value
	private Map<String, InterfaceConfigurationBean> fieldIdToConfigBean;

	private Logger logger = Logger.getLogger(MapPreparedStatementSetter.class);

	/**
	 * This method receives a flat record of AttributesBean it sets values to
	 * prepared statement. It skips following while inserting to TARGET table 
	 * 1.FILLER
	 * 2. LEVEL
	 */
	public void setValues(Map<String, String> item, PreparedStatement ps)
			throws SQLException {
		Set<String> keys = item.keySet();
		StringBuilder values = new StringBuilder();
		int count = 1;
		// sort based on field id
		Set<String> sortedKeys = new TreeSet<String>(keys);
		for (String key : sortedKeys) {
			// Level record is just a place holder
			// Don't insert it into DB
			if (!Constants.LEVEL.equals(key)) {
				String targetColumn = fieldIdToConfigBean.get(key)
						.getTargetColumn();

				// Skip filler records
				if (!Constants.FILLER.equals(targetColumn)) {
					ps.setString(count, item.get(key));
					values.append(item.get(key) + " ");
					count++;
				}
			}

		}
		logger.info("Values to be inserted in the table " + values);

	}

	public Map<String, InterfaceConfigurationBean> getFieldIdToConfigBean() {
		return fieldIdToConfigBean;
	}

	public void setFieldIdToConfigBean(
			Map<String, InterfaceConfigurationBean> fieldIdToConfigBean) {
		this.fieldIdToConfigBean = fieldIdToConfigBean;
	}

}
