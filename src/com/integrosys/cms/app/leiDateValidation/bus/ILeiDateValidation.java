package com.integrosys.cms.app.leiDateValidation.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ILeiDateValidation  extends Serializable, IValueObject {

	
	public long getId();
	public void setId(long id);
	public long getVersionTime() ;
	public void setVersionTime(long versionTime);
	
	public String getStatus() ;
	public void setStatus(String status);
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	public String getCreateBy();
	public void setCreateBy(String createBy);
	public Date getLastUpdateDate();
	public void setLastUpdateDate(Date lastUpdateDate);
	public String getLastUpdateBy();
	public void setLastUpdateBy(String lastUpdateBy);
	public String getPartyID() ;
	public void setPartyID(String partyID);
	public String getPartyName();
	public void setPartyName(String PartyName);
	public String getLeiDateValidationPeriod();
	public void setLeiDateValidationPeriod(String leiDateValidationPeriod) ;
}
