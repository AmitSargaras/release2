package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

public interface IFacilityCoBorrowerDetails extends Serializable {

	public Long getId();
	public void setId(Long id);
	
	public String getCreateBy();
	public void setCreateBy(String createBy);
	
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public Long getMainProfileId();
	public void setMainProfileId(Long mainProfileId);
	
	public long getLimitId();
	public void setLimitId(long limitId);
	
	public String getCoBorrowerLiabId();
	public void setCoBorrowerLiabId(String coBorrowerLiabId);
	
	public String getCoBorrowerName();
	public void setCoBorrowerName(String coBorrowerName);
}
