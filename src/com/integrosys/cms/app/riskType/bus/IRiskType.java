package com.integrosys.cms.app.riskType.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IRiskType extends Serializable, IValueObject{

	public String getRiskTypeName(); 
	public void setRiskTypeName(String riskTypeName);
	
	public String getRiskTypeCode(); 
	public void setRiskTypeCode(String riskTypeCode);
	
	
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
