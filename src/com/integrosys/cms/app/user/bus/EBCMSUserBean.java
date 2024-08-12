package com.integrosys.cms.app.user.bus;

import java.util.Collection;
import java.util.Date;

import com.integrosys.component.user.app.bus.EBCommonUserBean;

/**
 * <dl>
 * <dt><b> Purpose: </b>
 * <dd>Entity bean implementation of user entity
 * <p>
 * <dt><b> Usage:</b>
 * <dd>
 * <p>
 * <dt><b>Version Control:</b>
 * <dd>$Revision: 1.1 $<br>
 * $Date: 2003/07/30 07:35:04 $<br>
 * $Author: kllee $<br>
 * </dl>
 */

public abstract class EBCMSUserBean extends EBCommonUserBean {
	/**
     */
	public EBCMSUserBean() {
		super();
	}

	public void setPassword(String pwd) {
		// do nothing
	}

	public String getPassword() {
		return null;
	}

    public abstract Long getEjbUserID();

    public abstract void setEjbUserID(Long aLong);

    public abstract void setLoginID(String s);

    public abstract void setUserName(String s);

    public abstract void setEmployeeID(String s);

    public abstract void setDepartment(String s);

    public abstract void setPosition(String s);

    public abstract void setPhoneNumber(String s);

    public abstract void setEmail(String s);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);

    public abstract String getStatus();

    public abstract void setStatus(String s);

    public abstract Date getValidFromDate();

    public abstract Date getValidToDate();

    public abstract void setValidFromDate(Date date);

    public abstract void setValidToDate(Date date);

    public abstract Long getEjbRoleID();

    public abstract void setEjbRoleID(Long aLong);

    public abstract String getLoginID();

    public abstract String getUserName();

    public abstract String getEmployeeID();

    public abstract String getDepartment();

    public abstract String getPosition();

    public abstract String getPhoneNumber();

    public abstract String getEmail();

    public abstract String getCountry();

    public abstract String getOrganisation();

    public abstract void setCountry(String s);

    public abstract void setOrganisation(String s);
    //Added by archana for HDFC - start
    
    public abstract Long getEjbTeamTypeMembershipID();

    public abstract void setEjbTeamTypeMembershipID(Long aLong);
    //Added by archana for HDFC - End 
    
// ADDED BY ABHIJIT R START
	

    public abstract Long getEjbCityId();

    public abstract void setEjbCityId(Long aLong);


    public abstract Long getEjbStateId();

    public abstract void setEjbStateId(Long aLong);


    public abstract Long getEjbCountryId();

    public abstract void setEjbCountryId(Long aLong);
    
    public abstract String getEjbBranchCode();

    public abstract void setEjbBranchCode(String aString);
    
//    public abstract String getEjbDepartmentCode();
//
//    public abstract void setEjbDepartmentCode(String aString);
    
    public abstract String getEjbAddress();

    public abstract void setEjbAddress(String aString);
    
    public abstract Long getEjbRegion();

    public abstract void setEjbRegion(Long aString);
    
    
    abstract public Collection getCMRUserSegment();

	abstract public void setCMRUserSegment(Collection col);
	
	abstract public Collection getCMRUserRegion();

	abstract public void setCMRUserRegion(Collection col);
    
    
    // Add by abhijit r end
    	
	public abstract String getIsacRefNumber();
    public abstract void setIsacRefNumber(String isacRefNumber);
    
    public abstract String getMakerDt();
    public abstract void setMakerDt(String makerDt);
    
    public abstract String getCheckerDt();
    public abstract void setCheckerDt(String checkerDt);
    
    
}
