/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * ICreditRiskParamEntry Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ICreditRiskParamEntry extends java.io.Serializable, IValueObject {

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

	long getCreditRiskParamEntryID();

	long getCreditRiskParamEntryRef();

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

	void setCreditRiskParamEntryID(long param);

	void setCreditRiskParamEntryRef(long param);

	void setVersionTime(long versionTime);

	void setBlackListed(String blackListed);

	void setSuspended(String suspended);

}
