package com.integrosys.cms.app.image.bus;
/**
*@author abhijit.rudrakshawar
*
*OB for Image Tag Details
*/

public class OBImageUploadDetails implements IImageUploadDetails {
	

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	private long id;
	
	
	private long versionTime;
	
	private String custId ;
	
		
	private String legalName;





	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	
	
	

}
