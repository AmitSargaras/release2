package com.integrosys.cms.app.facilityNewMaster.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface IFacilityNewMaster extends Serializable, IValueObject {


	public String getNewFacilityCode();
	public void setNewFacilityCode(String newFacilityCode);
	
	public String getNewFacilityName();
	public void setNewFacilityName(String newFacilityName);
	
	public String getNewFacilityCategory();
	public void setNewFacilityCategory(String newFacilityCategory);
	
	public String getNewFacilityType();
	public void setNewFacilityType(String newFacilityType);
	
	public String getNewFacilitySystem();
	public void setNewFacilitySystem(String system);
	
	public String getLineNumber();
	public void setLineNumber(String lineNumber);
	
	public String getPurpose();
	public void setPurpose(String purpose);
	
	public double getWeightage();
	public void setWeightage(double weightage);
	 
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
    
    public String getCpsId() ;
	public void setCpsId(String cpsId) ;
    
	public String getOperationName() ;
	public void setOperationName(String operationName) ;
	
	public String getRuleId();
	public void setRuleId(String ruleId);
	
	public String getProductAllowed();
	public void setProductAllowed(String productAllowed);
	
	public String getCurrencyRestriction();
	public void setCurrencyRestriction(String currencyRestriction);
	
	public String getRevolvingLine();
	public void setRevolvingLine(String revolvingLine);
	
	public String getLineCurrency();
	public void setLineCurrency(String lineCurrency);
	
	public String getIntradayLimit();
	public void setIntradayLimit(String intradayLimit);
	
	public String getStlFlag();
	public void setStlFlag(String stlFlag);
	
	public String getLineDescription();
	public void setLineDescription(String lineDescription);
	
	public String getScmFlag();
	public void setScmFlag(String scmFlag);
	
	public String getSelectedRiskTypes();
	public void setSelectedRiskTypes(String selectedRiskTypes);
	
	public String getAvailAndOptionApplicable();
	public void setAvailAndOptionApplicable(String availAndOptionApplicable);
	
	public String getLineExcludeFromLoa();
	public void setLineExcludeFromLoa(String lineExcludeFromLoa);
	
	public String getIdlApplicableFlag(); 
	public void setIdlApplicableFlag(String idlApplicableFlag);
}
