/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/13 08:41:24 $ Tag: $Name: $
 * 
 */
public interface IForexFeedEntry extends java.io.Serializable, IValueObject {
	BigDecimal getBuyRate();

	BigDecimal getSellRate();

	String getSellCurrency();

	String getBuyCurrency();
	
	String getCurrencyIsoCode();
	
	String getCurrencyDescription();
	
	String getStatus();
	
	String getRestrictionType();

	int getBuyUnit();

	int getSellUnit();

	long getForexFeedEntryID();

	long getForexFeedEntryRef();

	void setBuyRate(BigDecimal param);

	void setSellRate(BigDecimal param);

	void setSellCurrency(String param);
	
	void setCurrencyIsoCode(String currencyIsoCode);
	
	void setCurrencyDescription(String currencyDescription);
	
	void setStatus(String status);
	
	void setRestrictionType(String restrictionType);

	void setBuyCurrency(String param);

	void setBuyUnit(int param);

	void setSellUnit(int param);

	void setForexFeedEntryID(long param);

	void setForexFeedEntryRef(long param);

	long getVersionTime();

	void setVersionTime(long versionTime);

	Date getEffectiveDate();

	void setEffectiveDate(Date date);
	
	public FormFile getFileUpload();
	
	public void setFileUpload(FormFile fileUpload);
	
	public String getSystemCode();

	public void setSystemCode(String systemCode);


}
