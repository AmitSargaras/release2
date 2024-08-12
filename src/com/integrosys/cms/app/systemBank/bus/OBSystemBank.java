/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/OBDiaryItem.java,v 1.7 2005/09/30 11:32:23 jtan Exp $
 */
package com.integrosys.cms.app.systemBank.bus;

//app

import java.util.Date;

import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;

/**
 * 
@author $Author: Abhijit R $
*/
public class OBSystemBank implements ISystemBank {
	
	


	/**
	 * constructor
	 */
	public OBSystemBank() {
		
	}
    private long id;
	private String systemBankCode;
	private String systemBankName;
	private String address;
//	private String cityTown;
//	private String state;
//	private String country;
//	private String region;
	
	
	private ICity cityTown;
	private IState state;
	private ICountry country;
	private IRegion region;
	
	
	
	private long contactNumber;
	private String contactMail;
	private String revisedContactMail;
	private long versionTime;
	private long faxNumber;
	
	private String systemBankId;
	
	private String status;
	private long masterId=0l;
	private String deprecated;
	private String code;
	
	private Date creationDate;
	
	
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String cpsId;
	
	
	public String getRevisedContactMail() {
		return revisedContactMail;
	}
	public void setRevisedContactMail(String revisedContactMail) {
		this.revisedContactMail = revisedContactMail;
	}
	
	public long getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(long faxNumber) {
		this.faxNumber = faxNumber;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getSystemBankCode() {
		return systemBankCode;
	}
	public void setSystemBankCode(String systemBankCode) {
		this.systemBankCode = systemBankCode;
	}
	public String getSystemBankName() {
		return systemBankName;
	}
	public void setSystemBankName(String systemBankName) {
		this.systemBankName = systemBankName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
//	public String getCityTown() {
//		return cityTown;
//	}
//	public void setCityTown(String cityTown) {
//		this.cityTown = cityTown;
//	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
//	public String getCountry() {
//		return country;
//	}
//	public void setCountry(String country) {
//		this.country = country;
//	}
//	public String getRegion() {
//		return region;
//	}
//	public void setRegion(String region) {
//		this.region = region;
//	}
	
	
	public String getContactMail() {
		return contactMail;
	}
	public ICity getCityTown() {
		return cityTown;
	}
	public void setCityTown(ICity cityTown) {
		this.cityTown = cityTown;
	}
	public IState getState() {
		return state;
	}
	public void setState(IState state) {
		this.state = state;
	}
	public ICountry getCountry() {
		return country;
	}
	public void setCountry(ICountry country) {
		this.country = country;
	}
	public IRegion getRegion() {
		return region;
	}
	public void setRegion(IRegion region) {
		this.region = region;
	}
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}
	public String getSystemBankId() {
		return systemBankId;
	}
	public void setSystemBankId(String systemBankId) {
		this.systemBankId = systemBankId;
	}

	
	
	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
		
	}
	
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getCode()
	{
		return code;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

	
}