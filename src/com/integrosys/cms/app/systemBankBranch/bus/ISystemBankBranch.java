package com.integrosys.cms.app.systemBankBranch.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
/**
 * @author  Abhijit R. 
 */
public interface ISystemBankBranch extends Serializable, IValueObject {

	
	 public String getSystemBankBranchCode();
	 public void setSystemBankBranchCode(String systemBankBranchCode);
    
	 public String getSystemBankBranchName();
	 public void setSystemBankBranchName(String systemBankBranchName);
	 
	 public ISystemBank getSystemBankCode();
	 public void setSystemBankCode(ISystemBank systemBankCode);
	 
	 public String getAddress();
	 public void setAddress(String address);
	 
//	 public String getCityTown();
//	 public void setCityTown(String cityTown);
//	 
//	 public String getRegion();
//	 public void setRegion(String region);
//	 
//	 public String getState();
//	 public void setState(String state);
//	 
//	 public String getCountry();
//	 public void setCountry(String country);
	 
		public ICity getCityTown();
		public void setCityTown(ICity cityTown);
		
		public IState getState() ;
		public void setState(IState state);

		public ICountry getCountry() ;
		public void setCountry(ICountry country) ;

		public IRegion getRegion();
		public void setRegion(IRegion region);
	 
	 public String getContactNumber();
	 public void setContactNumber(String contactNumber);

	 
	 public String getFaxNumber();
	 public void setFaxNumber(String faxNumber);

	 public String getRbiCode();
	 public void setRbiCode(String rbiCode);
	 
	 
	 public String getIsHub();
	 public void setIsHub(String isHub);
	 
	 
	 public String getLinkedHub();
	 public void setLinkedHub(String linkedHub);
	 
	 
	 
	 public String getIsVault();
	 public void setIsVault(String isVault);
	 
	 
	 public String getCustodian1();
	 public void setCustodian1(String custodian1);
	 
	 

	 public String getCustodian2();
	 public void setCustodian2(String custodian1);
	 
	 
	 
	 
	 public String getContactMail();
	 public void setContactMail(String contactMail);
	 
	 
	 public String getCode();
	 
	 
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

    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);
    
    public String getOperationName() ;
	public void setOperationName(String operationName);
	
	public String getCpsId();
	public void setCpsId(String cpsId);
	
	public String getPincode();
	public void setPincode(String pincode);
}
