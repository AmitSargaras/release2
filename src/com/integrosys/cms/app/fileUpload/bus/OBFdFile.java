package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;


public class OBFdFile extends OBCommonFdFile{
	
public OBFdFile(){
	super();
	}
	
	private String cmsRefId;

	public String getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(String cmsRefId) {
		this.cmsRefId = cmsRefId;
	}
}
