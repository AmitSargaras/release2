/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * IPriceFeedEntry Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IPriceFeedEntry extends java.io.Serializable, IValueObject {

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

	long getPriceFeedversionTime();

	String getBlackListed();

	String getSuspended();

	String getLastUpdatedBy();

	long getTotalOutstandingUnit();

	long getFeedGroupId();

	String getFundManagerCode();

	String getProductCode();

	Date getMaturityDate();

	String getRating();

	String getStockType();

	String getIsAcceptable();

	String getStockCode();

	String getIsFi();

	String getBoardType();

	String getShareStatus();

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

	void setPriceFeedversionTime(long versionTime);

	void setBlackListed(String blackListed);

	void setSuspended(String suspended);

	void setLastUpdatedBy(String lastUpdatedBy);

	void setTotalOutstandingUnit(long totalOutstandingUnit);

	void setFeedGroupId(long feedGroupId);

	void setFundManagerCode(String fundManagerCode);

	void setProductCode(String productCode);

	void setMaturityDate(Date maturityDate);

	void setRating(String rating);

	void setStockType(String stockType);

	void setIsAcceptable(String isAcceptable);

	void setStockCode(String stockCode);

	void setIsFi(String isFi);

	void setBoardType(String boardType);

	void setShareStatus(String shareStatus);

}
