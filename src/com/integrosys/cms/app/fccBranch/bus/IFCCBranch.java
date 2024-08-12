package com.integrosys.cms.app.fccBranch.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/****
 * 
 * @author komal.agicha
 *
 */
public interface IFCCBranch extends Serializable, IValueObject {


    public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	

    public String getStatus();
    public void setStatus(String aStatus);	

    public long getVersionTime();
    public void setVersionTime(long versionTime);

    public String getBranchCode();
	public void setBranchCode(String branchCode);
	
	public String getBranchName();
	public void setBranchName(String branchName);
	
	public String getLastUpdatedBy() ;
	public void setLastUpdatedBy(String lastUpdatedBy) ;
	public String getCreatedBy() ;
	public void setCreatedBy(String createdBy);
	public Date getLastUpdatedOn();
	public void setLastUpdatedOn(Date lastUpdatedOn);
	public Date getCreatedOn();
	public void setCreatedOn(Date createdOn) ;
	public String getAliasBranchCode();
	public void setAliasBranchCode(String branchCode);
	

}
