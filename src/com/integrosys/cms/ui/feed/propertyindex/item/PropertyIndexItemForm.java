package com.integrosys.cms.ui.feed.propertyindex.item;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * This class implements FormBean
 */
public class PropertyIndexItemForm extends CommonForm implements java.io.Serializable {

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(String param) {
		this.unitPrice = param;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER } };
	}

	public static final String MAPPER = "com.integrosys.cms.ui.feed.propertyindex.item.PropertyIndexItemMapper";

	private String type;

	private String region;

	private String unitPrice;
}
