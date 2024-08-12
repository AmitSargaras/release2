package com.integrosys.cms.app.imageTag.bus;

/**
*@author abhijit.rudrakshawar
*
*OB for Image Tag Map
*/


public class OBImageTagMap implements IImageTagMap {
	
	private long imageId;

	private long tagId;

	private long versionTime;

	private String untaggedStatus ="N";
	
	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getUntaggedStatus() {
		return untaggedStatus;
	}

	public void setUntaggedStatus(String untaggedStatus) {
		this.untaggedStatus = untaggedStatus;
	}

	
		
	}
	
	