package com.integrosys.cms.app.feed.bus.gold;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IGoldFeedEntry extends java.io.Serializable, IValueObject {
	
	public long getGoldFeedEntryID();
	public void setGoldFeedEntryID(long goldFeedEntryID);
	public long getVersionTime();
	public void setVersionTime(long versionTime);
	public String getGoldGradeNum();
	public void setGoldGradeNum(String goldGradeNum);
	public String getUnitMeasurementNum();
	public void setUnitMeasurementNum(String unitMeasurementNum);
	public String getCurrencyCode();
	public void setCurrencyCode(String currencyCode);
	public BigDecimal getUnitPrice();
	public void setUnitPrice(BigDecimal unitPrice);
	public Date getLastUpdatedDate();
	public void setLastUpdatedDate(Date lastUpdatedDate);
	public long getGoldFeedEntryRef();
	public void setGoldFeedEntryRef(long goldFeedEntryRef);
	public long getGoldFeedGroupId();
	public void setGoldFeedGroupId(long goldFeedGroupId);
}
