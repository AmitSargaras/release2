package com.integrosys.cms.app.geography.country.bus;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ICountry extends IValueObject,Serializable{

	public String getCountryName();
	public void setCountryName(String countryCode);
    
	public String getCountryCode();
	public void setCountryCode(String countryCode);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);	
	
	public Set getRegionList();
	public void setRegionList(Set regionList);
	
	public long getIdCountry();
	public void setIdCountry(long countryId);
	

	public String getCpsId();
	public void setCpsId(String cpsId);
	public String getOperationName();
	public void setOperationName(String operation);
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() ;
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) ;
}
