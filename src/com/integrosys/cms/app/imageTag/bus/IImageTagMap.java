package com.integrosys.cms.app.imageTag.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface for Image Tag
 * @author abhijit.rudrakshawar
 
 */

public interface IImageTagMap extends Serializable, IValueObject {

	public long getImageId();
	
	public void setImageId(long imageId);
	

	public long getTagId();
	
	public void setTagId(long tagId);
	
	
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	

	public String getUntaggedStatus();
	
	public void setUntaggedStatus(String untaggedStatus);



}
