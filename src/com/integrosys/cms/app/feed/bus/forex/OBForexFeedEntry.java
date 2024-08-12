/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/OBForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/13 08:41:24 $ Tag: $Name: $
 * 
 */
public class OBForexFeedEntry implements IForexFeedEntry {

	private BigDecimal buyRate;

	private BigDecimal sellRate;

	private String sellCurrency;

	private String buyCurrency;
	
	private String systemCode;
	
	private String currencyIsoCode;
	
	private String currencyDescription;

	private String status;
	
	private String restrictionType;


	public String getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}

	private int buyUnit;

	private int sellUnit;

	private long forexFeedEntryID;

	private long forexFeedEntryRef;

	private long versionTime;

	private Date effectiveDate;
	
	private FormFile fileUpload;
	
	private String cpsId;
	
	
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

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

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public long getForexFeedEntryRef() {
		return forexFeedEntryRef;
	}

	public void setForexFeedEntryRef(long forexFeedEntryRef) {
		this.forexFeedEntryRef = forexFeedEntryRef;
	}

	public BigDecimal getBuyRate() {
		return this.buyRate;
	}

	public BigDecimal getSellRate() {
		return this.sellRate;
	}

	public String getSellCurrency() {
		return this.sellCurrency;
	}

	public String getBuyCurrency() {
		return this.buyCurrency;
	}

	public int getBuyUnit() {
		return this.buyUnit;
	}

	public int getSellUnit() {
		return this.sellUnit;
	}

	public long getForexFeedEntryID() {
		return this.forexFeedEntryID;
	}

	public void setBuyRate(BigDecimal param) {
		this.buyRate = param;
	}

	public void setSellRate(BigDecimal param) {
		this.sellRate = param;
	}

	public void setSellCurrency(String param) {
		this.sellCurrency = param;
	}

	public void setBuyCurrency(String param) {
		this.buyCurrency = param;
	}

	public void setBuyUnit(int param) {
		this.buyUnit = param;
	}

	public void setSellUnit(int param) {
		this.sellUnit = param;
	}

	public void setForexFeedEntryID(long param) {
		this.forexFeedEntryID = param;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date _effectiveDate) {
		this.effectiveDate = _effectiveDate;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((buyCurrency == null) ? 0 : buyCurrency.hashCode());
		result = prime * result + ((buyRate == null) ? 0 : buyRate.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final OBForexFeedEntry other = (OBForexFeedEntry) obj;
		if (buyCurrency == null) {
			if (other.buyCurrency != null) {
				return false;
			}
		}
		else if (!buyCurrency.equals(other.buyCurrency)) {
			return false;
		}

		if (buyRate == null) {
			if (other.buyRate != null) {
				return false;
			}
		}
		else if (!buyRate.equals(other.buyRate)) {
			return false;
		}

		return true;
	}

	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

}