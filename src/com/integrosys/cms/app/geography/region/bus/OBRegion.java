package com.integrosys.cms.app.geography.region.bus;

import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.country.bus.ICountry;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBRegion implements IRegion{
	
	private long id;
	private long idRegion;
	
	private ICountry countryId;
	private String regionCode;
	private String regionName;     
    private String status;
    private String deprecated;
    
    private Set stateList;
    
    private FormFile fileUpload;
    private String cpsId;
    
    /*dilshad Changes*/
    private String operationName;
    /*End Dilshad*/
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
    
	public long getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(long idRegion) {
		this.idRegion = idRegion;
	}
	public Set getStateList() {
		return stateList;
	}
	public void setStateList(Set stateList) {
		this.stateList = stateList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ICountry getCountryId() {
		return countryId;
	}
	public void setCountryId(ICountry countryId) {
		this.countryId = countryId;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
