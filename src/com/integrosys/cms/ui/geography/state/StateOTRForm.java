package com.integrosys.cms.ui.geography.state;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class StateOTRForm extends TrxContextForm implements Serializable{
	
	static final long serialVersionUID = 0L;
		
	private String id;
	private String countryId;
	private String regionId;
	
	private String stateCode;
	private String stateName;
	private String status;
    private String deprecated;    
    //private List countryList;
    //private List regonList;
    private String cpsId;
    private String operationName;
    
	
	
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
	
   
	/*public List getCountryList() {
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
	}*/
	
	
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
	public String[][] getMapper() {

		String[][] input = {
					{ "theOBTrxContext", TRX_MAPPER },
					{ "stateObj", STATE_MAPPER }
				};
		return input;
	}

	public static final String STATE_MAPPER = "com.integrosys.cms.ui.geography.state.StateOTRMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
}