package com.integrosys.cms.ui.feed.stock.list;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class StockListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.feed.stock.list.StockListMapper";

	private String[] tickerCodes;

	private String[] updatedUnitPrices;

	private String[] expiryDate;
	
	private String[] scriptNameArr;
	
	private String[] scriptValueArr;
	
	private String[] exchangeNameArr;
	
	private String[] faceValueArr;

	private String[] chkDeletes;

	private String[] chkBlackListeds;

	private String[] chkSuspendeds;

	private String targetOffset = "-1";

	private String subType = "";

	private String trxId = "";

	private String stockType = "";
	

	/**
	 * @return the scriptNameArr
	 */
	public String[] getScriptNameArr() {
		return scriptNameArr;
	}

	/**
	 * @param scriptNameArr the scriptNameArr to set
	 */
	public void setScriptNameArr(String[] scriptNameArr) {
		this.scriptNameArr = scriptNameArr;
	}

	/**
	 * @return the scriptValueArr
	 */
	public String[] getScriptValueArr() {
		return scriptValueArr;
	}

	/**
	 * @param scriptValueArr the scriptValueArr to set
	 */
	public void setScriptValueArr(String[] scriptValueArr) {
		this.scriptValueArr = scriptValueArr;
	}

	/**
	 * @return the exchangeNameArr
	 */
	public String[] getExchangeNameArr() {
		return exchangeNameArr;
	}

	/**
	 * @param exchangeNameArr the exchangeNameArr to set
	 */
	public void setExchangeNameArr(String[] exchangeNameArr) {
		this.exchangeNameArr = exchangeNameArr;
	}

	/**
	 * @return the faceValueArr
	 */
	public String[] getFaceValueArr() {
		return faceValueArr;
	}

	/**
	 * @param faceValueArr the faceValueArr to set
	 */
	public void setFaceValueArr(String[] faceValueArr) {
		this.faceValueArr = faceValueArr;
	}

	public String[] getChkBlackListeds() {
		return chkBlackListeds;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
	}

	public String[] getChkSuspendeds() {
		return chkSuspendeds;
	}

	public String[] getExpiryDate() {
		return expiryDate;
	}

	public String getExpiryDate(int i) {
		return expiryDate[i];
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

	public String getStockType() {
		return stockType;
	}

	public String getSubType() {
		return subType;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public String getTrxId() {
		return trxId;
	}

	public String[] getUpdatedUnitPrices() {
		return updatedUnitPrices;
	}

	public String getUpdatedUnitPrices(int i) {
		return updatedUnitPrices[i];
	}

	public void setChkBlackListeds(String[] chkBlackListeds) {
		this.chkBlackListeds = chkBlackListeds;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public void setChkSuspendeds(String[] chkSuspendeds) {
		this.chkSuspendeds = chkSuspendeds;
	}

	public void setExpiryDate(String[] expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public void setUpdatedUnitPrices(String[] updatedUnitPrices) {
		this.updatedUnitPrices = updatedUnitPrices;
	}

	public String[] getTickerCodes() {
		return tickerCodes;
	}

	public void setTickerCodes(String[] tickerCodes) {
		this.tickerCodes = tickerCodes;
	}
}
