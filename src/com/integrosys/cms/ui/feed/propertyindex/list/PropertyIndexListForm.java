package com.integrosys.cms.ui.feed.propertyindex.list;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class PropertyIndexListForm extends TrxContextForm implements java.io.Serializable {

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

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public static final String MAPPER = "com.integrosys.cms.ui.feed.propertyindex.list.PropertyIndexListMapper";

	private String[] updatedUnitPrices;

	private String[] chkDeletes;

	private String targetOffset = "-1";

	private String countryCode;

	private String trxId;
}
