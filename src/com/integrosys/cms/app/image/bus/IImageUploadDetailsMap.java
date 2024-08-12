package com.integrosys.cms.app.image.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface for Image Tag
 * @author abhijit.rudrakshawar
 
 */

public interface IImageUploadDetailsMap extends Serializable, IValueObject {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	
	public long getImageId();
	
	public void setImageId(long imageId);
	

	public long getUploadId();
	
	public void setUploadId(long uploadId);
	
	
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	

	



}
