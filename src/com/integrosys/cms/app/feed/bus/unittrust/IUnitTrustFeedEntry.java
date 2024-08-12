/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/IUnitTrustFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public interface IUnitTrustFeedEntry extends java.io.Serializable, IValueObject {

	String getFundManagerCode();

	String getProductCode();

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

	String getRating();

	double getOffer();
	
	Long getFundSize();
	
	Date getFundSizeUpdateDate();
	
	Date getDateLaunched();

	long getUnitTrustFeedEntryID();

	long getUnitTrustFeedEntryRef();

	long getVersionTime();

	void setFundManagerCode(String fundManagerCode);

	void setProductCode(String productCode);

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
	
	void setFundSize(Long fundSize);
	
	void setFundSizeUpdateDate(Date fundSizeUpdateDate);
	
	void setDateLaunched(Date dateLaunched);

	void setUnitTrustFeedEntryID(long param);

	void setUnitTrustFeedEntryRef(long param);

	void setVersionTime(long versionTime);

	void setRating(String rating);

	public String getFundCode();

	public void setFundCode(String funCode);

}
