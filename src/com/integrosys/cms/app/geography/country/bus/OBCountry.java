package com.integrosys.cms.app.geography.country.bus;

import java.util.Set;

import org.apache.struts.upload.FormFile;

public class OBCountry implements ICountry{
	
	private long id;
	private long  idCountry;
	
	private String countryCode;
	private String countryName;    
    private String status;
    private String deprecated;
    private String cpsId;
    
    private Set regionList;
    
    
    private FormFile fileUpload;
   
    private String operationName;
  
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(long idCountry) {
		this.idCountry = idCountry;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
	public Set getRegionList() {
		return regionList;
	}
	public void setRegionList(Set regionList) {
		this.regionList = regionList;
	}
	public long getVersionTime() {
		return 0;
	}
	public void setVersionTime(long arg0) {		
	}   
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
