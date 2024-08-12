package com.integrosys.cms.app.image.bus;

/**
*@author abhijit.rudrakshawar
*
*OB for Image Tag Map
*/


public class OBImageUploadDetailsMap implements IImageUploadDetailsMap {
	
	private long imageId;

	private long uploadId;

	private long versionTime;
	
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

	public long getUploadId() {
		return uploadId;
	}

	public void setUploadId(long uploadId) {
		this.uploadId = uploadId;
	}

	
		
	}
	
	