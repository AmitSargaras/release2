package com.integrosys.cms.app.valuationAgency.bus;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;

public class OBValuationAgency implements IValuationAgency {

	private long id;
	private String valuationAgencyCode;
	private String valuationAgencyName;
	private String address;
	private ICity cityTown;
	private IState state;
	private ICountry country;
	private IRegion region;
	
	private long versionTime;
	private String status;
	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	private FormFile fileUpload;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValuationAgencyCode() {
		return valuationAgencyCode;
	}

	public void setValuationAgencyCode(String valuationAgencyCode) {
		this.valuationAgencyCode = valuationAgencyCode;
	}

	public String getValuationAgencyName() {
		return valuationAgencyName;
	}

	public void setValuationAgencyName(String valuationAgencyName) {
		this.valuationAgencyName = valuationAgencyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
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

	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

}
