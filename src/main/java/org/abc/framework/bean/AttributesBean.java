package org.abc.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abc.framework.bean.common.Constants;

/*******************************************************************************
 * File Name   :   AttributesBean.java 
 * Description : General Purpose bean created
 * 				 for writing multiple level records
 * 				it contains map for current level
 * 				and list of AttributesBean for the next level
 * @author :  
 * Date : 04/05/2012 02:40:00 PM
 * @version : 1.0
 * 
 *****************************************************************************/

public class AttributesBean {

	private Map<String, String> attributes = new HashMap<String, String>();

	private List<AttributesBean> varryingFields = new ArrayList<AttributesBean>();

	/**
	 * Constructor for class AttributesBean.
	 */
	public AttributesBean() {
		attributes = new HashMap<String, String>();
	}

	/**
	 * @param attributeName_
	 *            String
	 * @param attributeValue_
	 *            String
	 */
	public void setAttribute(final String attributeName_,
			final String attributeValue_) {
		attributes.put(attributeName_, attributeValue_);
	}

	/**
	 * @param attributeName_
	 *            String
	 * @return String
	 */
	public String getAttributeValue(final String attributeName_) {
		return (String) attributes.get(attributeName_);
	}

	/**
	 * Method equals() overridden for implementing business logic.
	 * 
	 * @param obj_
	 *            Object
	 * @return boolean
	 */

	public boolean equals(final Object obj_) {
		if (obj_ instanceof AttributesBean) {
			final AttributesBean newAttributesBean = (AttributesBean) obj_;
			for (String attributeName : newAttributesBean.attributes.keySet()) {
				if (!newAttributesBean.getAttributeValue(attributeName).equals(
						getAttributeValue(attributeName))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method toString() overridden for implementing business logic.
	 * 
	 * @return String
	 */

	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(attributes.toString());
		stringBuilder.append(Constants.LINE_TAB_BREAK);
		if (getVarryingFields() != null) {
			for (AttributesBean varryingAttributesBean : getVarryingFields()) {
				stringBuilder.append(varryingAttributesBean.toString());
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * Method hashCode() overridden for implementing business logic.
	 * 
	 * @return int
	 */
	public int hashCode() {
		int result = 0;
		for (String attributeName : attributes.keySet()) {
			result += getAttributeValue(attributeName) != null
					? getAttributeValue(attributeName).hashCode()
					: 0;
		}
		return result;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes_
	 *            the attributes to set
	 */
	public void setAttributes(final Map<String, String> attributes_) {
		this.attributes = attributes_;
	}

	/**
	 * @return the varryingFields
	 */
	public List<AttributesBean> getVarryingFields() {
		return varryingFields;
	}

	/**
	 * @param varryingFields_
	 *            the varryingFields to set
	 */
	public void setVarryingFields(final List<AttributesBean> varryingFields_) {
		this.varryingFields = varryingFields_;
	}

	/**
	 * This method converts hierarchical data to flat structure as a 
	 * Cartesian product of multiple levels
	 * 1- data level 1
	 * 		2-data level 2 1
	 *  	2-data level 2 2
	 * return list of map would contain
	 * Item 1 = data level 1 , data level 2 1
	 * Item 2 = data level 1 , data level 2 2
	 * @return
	 */
	public List<Map<String, String>> getListHashMap() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		if (varryingFields != null && varryingFields.size() != 0) {
			Map<String, String> currentLevelMap = null;
			for (AttributesBean nextLevelAttributesBean : varryingFields) {
				List<Map<String, String>> nextLevelListHashMap = nextLevelAttributesBean
						.getListHashMap();
				for (Map<String, String> nextLevelHashMap : nextLevelListHashMap) {
					currentLevelMap = new HashMap<String, String>();
					currentLevelMap.putAll(attributes);
					currentLevelMap.putAll(nextLevelHashMap);
					result.add(currentLevelMap);
				}
			}
		} else {
			Map<String, String> currentLevelMap = new HashMap<String, String>();
			currentLevelMap.putAll(attributes);
			result.add(currentLevelMap);
		}
		return result;
	}

}
