package com.integrosys.cms.app.geography.region.bus;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.country.bus.ICountry;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public interface IRegion extends IValueObject,Serializable{

	public ICountry getCountryId();
	public void setCountryId(ICountry countryid);
	 
	public String getRegionName();
	public void setRegionName(String regionCode);
    
	public String getRegionCode();
	public void setRegionCode(String regionCode);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);	
	
	public Set getStateList();
	public void setStateList(Set stateList);
	
	public long getIdRegion();
	public void setIdRegion(long idRegion);
	/*dilshad Chnages for test*/
	public String getCpsId();
	public void setCpsId(String cpsId);
	public String getOperationName();
	public void setOperationName(String operation);
	
	/**
	
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() ;

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) ;

}
