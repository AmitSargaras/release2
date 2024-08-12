/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/IStockFeedEntry.java,v 1.5 2003/08/14 10:57:07 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/14 10:57:07 $ Tag: $Name: $
 * 
 */
public interface IStockFeedEntry extends java.io.Serializable, IValueObject {
	/**
	 * @return the scriptValue
	 */
	public double getScriptValue() ;

	/**
	 * @param scriptValue the scriptValue to set
	 */
	public void setScriptValue(double scriptValue) ;
	
	/**
	 * @return the scriptCode
	 */
	public String getScriptCode() ;

	/**
	 * @param scriptCode the scriptCode to set
	 */
	public void setScriptCode(String scriptCode) ;

	/**
	 * @return the scriptName
	 */
	public String getScriptName() ;

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName) ;

	/**
	 * @return the faceValue
	 */
	public double getFaceValue() ;

	/**
	 * @param faceValue the faceValue to set
	 */
	public void setFaceValue(double faceValue);

	/**
	 * @return the stockExchangeName
	 */
	public String getStockExchangeName() ;

	/**
	 * @param stockExchangeName the stockExchangeName to set
	 */
	public void setStockExchangeName(String stockExchangeName);
	
	

	String getTicker();

	String getRic();

	String getType();

	String getCountryCode();

	String getExchange();

	double getUnitPrice();

	String getCurrencyCode();

	String getIsinCode();

	String getFundManagerName();

	Date getLastUpdatedDate();

	double getOffer();

	long getStockFeedEntryID();

	long getStockFeedEntryRef();

	long getVersionTime();

	String getBlackListed();

	String getSuspended();

	void setTicker(String ticker);

	void setRic(String ric);

	void setType(String type);

	void setCountryCode(String countryCode);

	void setExchange(String exchange);

	void setUnitPrice(double unitPrice);

	void setCurrencyCode(String currencyCode);

	void setIsinCode(String isinCode);

	void setFundManagerName(String fundManagerName);

	void setLastUpdatedDate(Date lastUpdatedDate);

	void setOffer(double offer);

	void setStockFeedEntryID(long param);

	void setStockFeedEntryRef(long param);

	void setVersionTime(long versionTime);

	void setBlackListed(String blackListed);

	void setSuspended(String suspended);

	Date getExpiryDate();

	void setExpiryDate(Date expiryDate);

	public FormFile getFileUpload();
	
	public void setFileUpload(FormFile fileUpload);
	
	public String getGroupStockType();

	public void setGroupStockType(String groupStockType);
}
