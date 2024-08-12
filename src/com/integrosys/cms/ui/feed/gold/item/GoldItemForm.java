package com.integrosys.cms.ui.feed.gold.item;

import java.math.BigDecimal;

import com.integrosys.base.uiinfra.common.CommonForm;

public class GoldItemForm extends CommonForm implements java.io.Serializable {

	private String goldGradeNum;

	private String goldGradeValue;

	private String unitMeasurementNum;

	private String unitMeasurementValue;

	private String unitPrice;

	private String currencyCode;

	public String getGoldGradeNum() {
		return goldGradeNum;
	}

	public void setGoldGradeNum(String goldGradeNum) {
		this.goldGradeNum = goldGradeNum;
	}

	public String getGoldGradeValue() {
		return goldGradeValue;
	}

	public void setGoldGradeValue(String goldGradeValue) {
		this.goldGradeValue = goldGradeValue;
	}

	public String getUnitMeasurementNum() {
		return unitMeasurementNum;
	}

	public void setUnitMeasurementNum(String unitMeasurementNum) {
		this.unitMeasurementNum = unitMeasurementNum;
	}

	public String getUnitMeasurementValue() {
		return unitMeasurementValue;
	}

	public void setUnitMeasurementValue(String unitMeasurementValue) {
		this.unitMeasurementValue = unitMeasurementValue;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it.
	 * 
	 * it has a syntax [(key, MapperClassname)]
	 * 
	 * 
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {

		return new String[][] { { MAPPER, MAPPER } };

	}

	public static final String MAPPER = "com.integrosys.cms.ui.feed.gold.item.GoldItemMapper";

}
