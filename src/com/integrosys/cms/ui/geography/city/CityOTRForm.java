package com.integrosys.cms.ui.geography.city;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CityOTRForm extends TrxContextForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String cityCode;
	private String cityName;	
	private String stateId;
	private String status;
	private String deprecated;
	private String cpsId;
	private String operationName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
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

		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "cityObj", CITY_MAPPER } };
		return input;
	}

	public static final String CITY_MAPPER = "com.integrosys.cms.ui.geography.city.CityOTRMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
}
