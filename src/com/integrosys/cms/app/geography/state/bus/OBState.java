package com.integrosys.cms.app.geography.state.bus;

import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.region.bus.IRegion;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBState implements IState{
	
	private long id;
	private long idState;
	
	private IRegion regionId;	
	private String stateCode;
	private String stateName;
    private String status;
    private String deprecated;
    
    private Set cityList;
    
    private String operationName;
    private String cpsId;
    private FormFile fileUpload;
	
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
    
	public Set getCityList() {
		return cityList;
	}
	public void setCityList(Set cityList) {
		this.cityList = cityList;
	}
	public long getId() {
		return id;
	}
	public long getIdState() {
		return idState;
	}
	public void setIdState(long idState) {
		this.idState = idState;
	}
	public void setId(long id) {
		this.id = id;
	}
	public IRegion getRegionId() {
		return regionId;
	}
	public void setRegionId(IRegion regionId) {
		this.regionId = regionId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
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
