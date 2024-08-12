package com.integrosys.cms.app.component.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IComponent extends Serializable, IValueObject {

	public long getId();
    public void setId(long id);	
    
    public String getStatus();
    public void setStatus(String aStatus);	

    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public String getDeprecated();
    public void setDeprecated(String deprecated);
    
    public String getComponentType();
    public void setComponentType(String componentType);
    
    public String getComponentCode();
    public void setComponentCode(String componentCode);
    
    public String getComponentName();
    public void setComponentName(String componentName);
    
    public String getHasInsurance();
    public void setHasInsurance(String hasInsurance);
    //Start:-------->Abhishek Naik
    public String getDebtors();
    public void setDebtors(String debtors);
    
    public String getAge();
    public void setAge(String age);
    // End:-------->Abhishek Naik 
    
    //start santosh
  	public String getComponentCategory();
  	public void setComponentCategory(String componentCategory);
  		
  	public String getApplicableForDp();
  	public void setApplicableForDp(String applicableForDp);
  	//end santosh
}
