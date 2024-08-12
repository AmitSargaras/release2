package com.integrosys.cms.ui.feed.stock.item;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * This class implements FormBean
 */
/*
 * Changes for File Upload
 * extends with TrxContextForm by govind and remove extended CommonForm due to use of TrxContextMapper
 * 
 * Old Class "StockItemForm extends CommonForm implements java.io.Serializable"
 * 
 */
public class StockItemForm extends TrxContextForm implements java.io.Serializable {

	/**
	 * @return the scriptCode
	 */
	public String getScriptCode() {
		return scriptCode;
	}

	/**
	 * @param scriptCode the scriptCode to set
	 */
	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}

	/**
	 * @return the scriptName
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * @return the scriptValue
	 */
	public String getScriptValue() {
		return scriptValue;
	}

	/**
	 * @param scriptValue the scriptValue to set
	 */
	public void setScriptValue(String scriptValue) {
		this.scriptValue = scriptValue;
	}

	/**
	 * @return the stockExchangeName
	 */
	public String getStockExchangeName() {
		return stockExchangeName;
	}

	/**
	 * @param stockExchangeName the stockExchangeName to set
	 */
	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the faceValue
	 */
	public String getFaceValue() {
		return faceValue;
	}

	/**
	 * @param faceValue the faceValue to set
	 */
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	
	/**
	 * @return the stockType
	 */
	public String getStockType() {
		return stockType;
	}

	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
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

		return new String[][] { { MAPPER, MAPPER },{ "theOBTrxContext", TRX_MAPPER } };

	}

	public static final String MAPPER = "com.integrosys.cms.ui.feed.stock.item.StockItemMapper";
	
	//Add by Govind, for File Upload
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	private String scriptCode = "";

	private String scriptName = "";

	private String scriptValue = "";
	
	private String stockExchangeName = "";
	
	private String lastUpdateDate = "";
	
	private String faceValue = "";
	
	private String stockType = "";
	
	private FormFile fileUpload;

	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

}
