package com.integrosys.cms.app.systemBank.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;

/**
 @author $Author: Abhijit R $
 */
public interface ISystemBank extends Serializable, IValueObject {

	 public String getSystemBankCode();
	 public void setSystemBankCode(String systemBankCode);
    
	 public String getSystemBankName();
	 public void setSystemBankName(String systemBankName);
    
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
	 
	 public long getContactNumber();
	 public void setContactNumber(long contactNumber);
	 
	 public long getFaxNumber();
	 public void setFaxNumber(long faxNumber);

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
    
    public String getRevisedContactMail();
	public void setRevisedContactMail(String revisedContactMail);
    
}
