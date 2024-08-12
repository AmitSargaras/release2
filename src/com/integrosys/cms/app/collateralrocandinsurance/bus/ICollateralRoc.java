package com.integrosys.cms.app.collateralrocandinsurance.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ICollateralRoc extends Serializable, IValueObject{

	public String getCollateralCategory();
	public void setCollateralCategory(String collateralCategory);
	
	public String getCollateralRocCode();
	public void setCollateralRocCode(String collateralRocCode);
	
	public String getCollateralRocDescription();
	public void setCollateralRocDescription(String collateralRocDescription);
	
	public String getIrbCategory();
	public void setIrbCategory(String irbCategory);
	
	public String getInsuranceApplicable();
	public void setInsuranceApplicable(String insuranceApplicable);
	
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
