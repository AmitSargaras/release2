package com.integrosys.cms.app.collateralNewMaster.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface ICollateralNewMaster extends Serializable, IValueObject {


	public String getNewCollateralCode();
	public void setNewCollateralCode(String newCollateralCode);
	
	
	public String getNewCollateralDescription();
	public void setNewCollateralDescription(String newCollateralDescription);
	
	
	public String getNewCollateralSubType() ;
	public void setNewCollateralSubType(String newCollateralSubType);
	
	
	public String getNewCollateralMainType();
	public void setNewCollateralMainType(String newCollateralMainType);
	
	
	public String getInsurance();
	public void setInsurance(String insurance);
	
	
	public String getRevaluationFrequencyDays();
	public void setRevaluationFrequencyDays(String revaluationFrequencyDays);
	public long getRevaluationFrequencyCount();
	public void setRevaluationFrequencyCount(long revaluationFrequencyCount);
	
	 
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
   
    public String getCreateBy();
    public void setCreateBy(String createBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    
    public String getOperationName();
	public void setOperationName(String operationName);
	public String getCpsId();
	public void setCpsId(String cpsId);
	
	public String getNewCollateralCategory();
	public void setNewCollateralCategory(String newCollateralCategory);
	public String getIsApplicableForCersaiInd();
	public void setIsApplicableForCersaiInd(String isApplicableForCersaiInd);
}
