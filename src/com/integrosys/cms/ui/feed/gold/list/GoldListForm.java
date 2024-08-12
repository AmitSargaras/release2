package com.integrosys.cms.ui.feed.gold.list;

import com.integrosys.cms.ui.common.TrxContextForm;

public class GoldListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.feed.gold.list.GoldListMapper";

	private String[] updatedUnitPrices;

	private String[] chkDeletes;

	private String[] currencyCodes;

	private String[] goldGrade;

	private String[] unitMeasurement;

	private String targetOffset = "-1";

	public String[] getUpdatedUnitPrices() {
		return updatedUnitPrices;
	}

	public String getUpdatedUnitPrices(int i) {
		return updatedUnitPrices[i];
	}

	public void setUpdatedUnitPrices(String[] updatedUnitPrices) {
		this.updatedUnitPrices = updatedUnitPrices;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String[] getCurrencyCodes() {
		return currencyCodes;
	}

	public void setCurrencyCodes(String[] currencyCodes) {
		this.currencyCodes = currencyCodes;
	}

	public String[] getGoldGrade() {
		return goldGrade;
	}

	public void setGoldGrade(String[] goldGrade) {
		this.goldGrade = goldGrade;
	}

	public String[] getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(String[] unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String toString() {
		return "\nupdatedUnitPrices = " + updatedUnitPrices + "\nchkDeletes = " + chkDeletes + "\ncurrencyCodes = "
				+ currencyCodes + "\ngoldGrade = " + goldGrade + "\nunitMeasurement = " + unitMeasurement;
	}
}
