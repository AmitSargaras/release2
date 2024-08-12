package com.integrosys.cms.app.valuationAmountAndRating.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IValuationAmountAndRating extends Serializable, IValueObject{

	public String getCriteria(); 
	public void setCriteria(String criteria);
	
	public String getValuationAmount(); 
	public void setValuationAmount(String valuationAmount);
	
	public String getRamRating(); 
	public void setRamRating(String ramRating);
	
	public String getExcludePartyId(); 
	public void setExcludePartyId(String excludePartyId);
	
	public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	

    public String getStatus();
    public void setStatus(String aStatus);	
    
    public long getMasterId();
    public void setMasterId(long masterId);	

    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public Date getCreationDate();
    public void setCreationDate(Date creationDate);
   
    public String getCreateBy();
    public void setCreateBy(String createBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    
	public String getOperationName();
	public void setOperationName(String operationName) ;
}
