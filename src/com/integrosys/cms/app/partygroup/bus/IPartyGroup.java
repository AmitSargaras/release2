package com.integrosys.cms.app.partygroup.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 @author $Author: Bharat Waghela $
 */
public interface IPartyGroup extends Serializable, IValueObject {


	public String getPartyCode(); 

	public void setPartyCode(String partyCode); 

	public String getPartyName(); 

	public void setPartyName(String partyName);

	public Amount getGroupExpLimit(); 

	public void setGroupExpLimit(Amount groupExpLimit);
	 
	
	 
	 
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
    
	public String getCpsId() ;
	public void setCpsId(String cpsId) ;

	public String getOperationName();
	public void setOperationName(String operationName) ;
    
}

