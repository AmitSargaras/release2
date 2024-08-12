package com.integrosys.cms.app.cersaiMapping.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ICersaiMapping extends Serializable, IValueObject {
	
	
	public long getId();
	public void setId(long id);
	public long getVersionTime() ;
	public void setVersionTime(long versionTime);
	
	public String getOperationName();
	public void setOperationName(String operationName) ;
	public String getStatus() ;
	public void setStatus(String status);
	public long getMasterId();
	public void setMasterId(long masterId);
	public String getDeprecated();
	public void setDeprecated(String deprecated);
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	public String getCreateBy();
	public void setCreateBy(String createBy);
	public Date getLastUpdateDate();
	public void setLastUpdateDate(Date lastUpdateDate);
	public String getLastUpdateBy();
	public void setLastUpdateBy(String lastUpdateBy);
	public String getClimsValue() ;
	public void setClimsValue(String climsValue);
	public String getCersaiValue();
	public void setCersaiValue(String cersaiValue);
	public String getMasterName();
	public void setMasterName(String masterName) ;
	public String[] getUpdatedCersaiValue();
	public void setUpdatedCersaiValue(String[] updatedCersaiValue);
	public String[] getClimsValues();
	public void setClimsValues(String[] climsValues);
	public void setFeedEntriesArr(ICersaiMapping[] feedEntriesArr);
	public ICersaiMapping[] getFeedEntriesArr();
	public void setMasterValueList(ICersaiMapping[] masterValueList);
	public ICersaiMapping[] getMasterValueList();
	public String[] getUpdatedClimsValue();
	public void setUpdatedClimsValue(String[] updatedClimsValue);
	
	
}
