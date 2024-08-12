package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.io.Serializable;
import java.util.Date;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface INpaTraqCodeMaster extends Serializable, IValueObject{

	public String getNpaTraqCode();
	public void setNpaTraqCode(String npaTraqCode);

	public String getSecurityType();
	public void setSecurityType(String securityType);

	public String getSecuritySubType();
	public void setSecuritySubType(String securitySubType);

	public String getPropertyTypeCodeDesc();
	public void setPropertyTypeCodeDesc(String propertyTypeCodeDesc);
	
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
