package com.integrosys.cms.app.image.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface for Image Tag
 * @author abhijit.rudrakshawar
 
 */

public interface IImageUploadDetails extends Serializable, IValueObject {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	

	public long getId();
	
	public void setId(long id);
	
	public String getLegalName();
	
	public void setLegalName(String securityId);

	
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	public String getCustId();

	public void setCustId(String custId);

	



}
