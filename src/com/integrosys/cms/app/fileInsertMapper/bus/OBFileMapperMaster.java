/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.fileInsertMapper.bus;

//app

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.systemBank.bus.ISystemBank;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBFileMapperMaster implements IFileMapperMaster {
	
	/**
	 * constructor
	 */
	public OBFileMapperMaster() {
		
	}
	
    private long id;
    private long fileId;
    private String transId;
    private long sysId;
    private long versionTime;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public long getSysId() {
		return sysId;
	}
	public void setSysId(long sysId) {
		this.sysId = sysId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}

}