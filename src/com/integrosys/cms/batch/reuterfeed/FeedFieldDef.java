/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedFieldDef implements Comparable {
	private int order;

	private String fieldName;

	private String fieldType;

	private String columnName;

	private Object fieldValue;

	private boolean required;

	/**
	 * @return Returns the columnName.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName The columnName to set.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return Returns the fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return Returns the fieldType.
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType The fieldType to set.
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return Returns the fieldValue.
	 */
	public Object getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue The fieldValue to set.
	 */
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return Returns the order.
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order The order to set.
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return Returns the required.
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required The required to set.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	public String toString() {
		return "ORDER: " + order + "FIELD NAME: " + fieldName + "FIELD TYPE: " + fieldType + "COLUMN NAME: "
				+ columnName + "FIELD VALUE: " + fieldValue + "REQUIRED: " + required;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof FeedFieldDef) {
			return this.getOrder() - ((FeedFieldDef) o).getOrder();
		}
		else {
			throw new ClassCastException();
		}
	}

}
