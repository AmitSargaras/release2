package com.integrosys.cms.app.geography.city.bus;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.state.bus.IState;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBCity implements ICity{
	
	private long id;	
	private long idCity;
	
	private String cityCode;
	private String cityName;     
	private IState stateId;
	private String status;
    private String deprecated;
    private String cpsId;
	private String operationName;
	
	
	
    private FormFile fileUpload;
    private Long ecbfCityId;
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
	public long getIdCity() {
		return idCity;
	}
	public void setIdCity(long idCity) {
		this.idCity = idCity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public IState getStateId() {
		return stateId;
	}
	public void setStateId(IState stateId) {
		this.stateId = stateId;
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
	
	public Long getEcbfCityId() {
		return ecbfCityId;
	}
	public void setEcbfCityId(Long ecbfCityId) {
		this.ecbfCityId = ecbfCityId;
	}
}
