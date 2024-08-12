package com.integrosys.cms.app.pincodemapping.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.state.bus.IState;

public interface IPincodeMapping extends Serializable, IValueObject{

	public long getPincode();
	public void setPincode(long pincode);
	
	/*public String getStateCode();
	public void setStateCode(String stateCode);*/
	
	public IState getStateId();
	public void setStateId(IState stateId);
	
    public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	

    public String getStatus();
    public void setStatus(String aStatus);	
    
    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public Date getCreationDate();
    public void setCreationDate(Date creationDate);
    
    public String getCreatedBy();
    public void setCreatedBy(String createdBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    
	public String getCpsId() ;
	public void setCpsId(String cpsId) ;

	public String getOperationName();
	public void setOperationName(String operationName) ;
    
}
