package com.integrosys.cms.app.customer.bus;

import java.io.Serializable;

public interface ICoBorrowerDetails extends Serializable  {

	public Long getId();
	public void setId(Long id);
	
	public long getMainProfileId();
	public void setMainProfileId(long mainProfileId);
	
	public String getCoBorrowerLiabId();
	public void setCoBorrowerLiabId(String coBorrowerLiabId);
	
	public String getCoBorrowerName();
	public void setCoBorrowerName(String coBorrowerName);
	
	public String getIsInterfaced();
	public void setIsInterfaced(String isInterfaced);
	
}
