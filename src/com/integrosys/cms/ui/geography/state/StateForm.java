package com.integrosys.cms.ui.geography.state;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class StateForm extends TrxContextForm implements Serializable{
	
	static final long serialVersionUID = 0L;
		
	private String id;
	private String countryId;
	private String regionId;
	
	private String stateCode;
	private String stateName;  
	
    private String countryOBId;
    private String regionOBId;

	private String status;
    private String deprecated;
    
    private String searchText;
    
    private List countryList;
    private List regonList;
    
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
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
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
	public String getRegionOBId() {
		return regionOBId;
	}
	public void setRegionOBId(String regionOBId) {
		this.regionOBId = regionOBId;
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
	
    public String getCountryOBId() {
		return countryOBId;
	}
	public void setCountryOBId(String countryOBId) {
		this.countryOBId = countryOBId;
	}
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List getCountryList() {
		return countryList;
	}
	public void setCountryList(List countryList) {
		this.countryList = countryList;
	}
	
	public List getRegonList() {
		return regonList;
	}
	public void setRegonList(List regonList) {
		this.regonList = regonList;
	}
	public String[][] getMapper() {

		String[][] input = {
					{ "theOBTrxContext", TRX_MAPPER },
					{ "stateObj", STATE_MAPPER }
				};
		return input;
	}

	public static final String STATE_MAPPER = "com.integrosys.cms.ui.geography.state.StateMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
}
