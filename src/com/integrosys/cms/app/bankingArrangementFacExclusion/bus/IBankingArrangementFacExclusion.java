package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IBankingArrangementFacExclusion extends Serializable, IValueObject{

	public String getSystem();
	public void setSystem(String system);

	public String getFacCategory();
	public void setFacCategory(String facCategory);

	public String getFacName();
	public void setFacName(String facName);

	public String getExcluded();
	public void setExcluded(String excluded);
	
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