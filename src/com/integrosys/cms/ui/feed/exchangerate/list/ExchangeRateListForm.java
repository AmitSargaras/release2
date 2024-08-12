package com.integrosys.cms.ui.feed.exchangerate.list;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class ExchangeRateListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.feed.exchangerate.list.ExchangeRateListMapper";

	private String[] updatedUnitPrices;
	
	private String[] updatedCurrencyDescription;

	private String[] chkDeletes;

	private String[] buyCurrencies;

	private String targetOffset = "-1";

	public String[] getBuyCurrencies() {
		return buyCurrencies;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
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

	public String getTargetOffset() {
		return targetOffset;
	}

	public String[] getUpdatedUnitPrices() {
		return updatedUnitPrices;
	}

	public String getUpdatedUnitPrices(int i) {
		return updatedUnitPrices[i];
	}

	public void setBuyCurrencies(String[] buyCurrencies) {
		this.buyCurrencies = buyCurrencies;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public void setUpdatedUnitPrices(String[] updatedUnitPrices) {
		this.updatedUnitPrices = updatedUnitPrices;
	}

	public String[] getUpdatedCurrencyDescription() {
		return updatedCurrencyDescription;
	}

	public void setUpdatedCurrencyDescription(String[] updatedCurrencyDescription) {
		this.updatedCurrencyDescription = updatedCurrencyDescription;
	}
	

}