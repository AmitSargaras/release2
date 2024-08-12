package com.integrosys.cms.app.caseBranch.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface ICaseBranch extends Serializable, IValueObject {


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
	
	public String getCoordinator1() ;
	public void setCoordinator1(String coordinator1);
	
	public String getCoordinator1Email() ;
	public void setCoordinator1Email(String coordinator1Email);
	
	public String getCoordinator2();
	public void setCoordinator2(String coordinator2) ;
	
	public String getCoordinator2Email() ;
	public void setCoordinator2Email(String coordinator2Email) ;

}
