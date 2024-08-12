package com.integrosys.cms.app.commoncodeentry.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ICommonCodeEntry extends java.io.Serializable, IValueObject {
	public String getEntryCode();

	public void setEntryCode(String entryCode);

	public String getEntryName();

	public void setEntryName(String entryName);

	public boolean getActiveStatus();

	public void setActiveStatus(boolean activeStatus);

	public String getEntrySource();

	public void setEntrySource(String entrySource);

	public String getCountry();

	public void setCountry(String country);

	public Integer getGroupId();

	public void setGroupId(Integer groupId);

	public String getCategoryCode();

	public void setCategoryCode(String categoryCode);

	public long getEntryId();

	public void setEntryId(long entryId);

	public long getCategoryCodeId();

	public void setCategoryCodeId(long categoryCodeId);

	public String getRefEntryCode();

	public void setRefEntryCode(String refEntryCode);

	public abstract String getStatus();

	public abstract void setStatus(String status);
	
	public String getCpsId();

	public void setCpsId(String cpsId);
	
}
