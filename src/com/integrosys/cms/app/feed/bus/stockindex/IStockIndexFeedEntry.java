/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/IStockIndexFeedEntry.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 * 
 */
public interface IStockIndexFeedEntry extends java.io.Serializable, IValueObject {

	String getTicker();

	String getRic();

	String getType();

	String getCountryCode();

	String getExchange();

	double getUnitPrice();

	String getCurrencyCode();

	String getIsinCode();

	String getName();

	String getFundManagerName();

	Date getLastUpdatedDate();

	double getOffer();

	long getStockIndexFeedEntryID();

	long getStockIndexFeedEntryRef();

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

	void setName(String name);

	void setFundManagerName(String fundManagerName);

	void setLastUpdatedDate(Date lastUpdatedDate);

	void setOffer(double offer);

	void setStockIndexFeedEntryID(long param);

	void setStockIndexFeedEntryRef(long param);

	void setVersionTime(long versionTime);

	void setBlackListed(String blackListed);

	void setSuspended(String suspended);

}
